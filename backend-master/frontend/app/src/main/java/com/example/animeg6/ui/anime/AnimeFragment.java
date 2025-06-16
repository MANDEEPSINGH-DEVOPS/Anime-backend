package com.example.animeg6.ui.anime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.animeg6.AdapterAnime;
import com.example.animeg6.MainActivity;
import com.example.animeg6.databinding.FragmentAnimeBinding;
import com.example.animeg6.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnimeFragment extends Fragment {

    private FragmentAnimeBinding binding;
    private static String url_get_animes_favoritos = "http://10.200.243.205:8091/users/1/animes";

    RecyclerView recyclerView;

    ProgressBar progressBarAnime;
    AdapterAnime adapterAnime;
    List<Anime> animes = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AnimeViewModel animeViewModel =
                new ViewModelProvider(this).get(AnimeViewModel.class);

        binding = FragmentAnimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBarAnime = binding.progressBarAnime;
        recyclerView = binding.recyclerAnime;
        recyclerView.setVisibility(View.INVISIBLE);
        getAnimesArray();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAnimesArray(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                url_get_animes_favoritos = "http://10.200.243.205:8091/users/"+obtenerUserID()+"/animes";

                animes = new ArrayList<>();
                RequestQueue queue = Volley.newRequestQueue(AnimeFragment.this.requireContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        url_get_animes_favoritos,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("sfsfs", "yaaaaaaaaaa");
                                for(int i=0;i< response.length();i++){
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        Anime anime = new Anime();
                                        anime.setId(Integer.parseInt(jsonObject.getString("id")));
                                        anime.setName(jsonObject.getString("name"));
                                        anime.setDescription(jsonObject.getString("description"));
                                        anime.setType(jsonObject.getString("type"));
                                        anime.setYear(Integer.parseInt(jsonObject.getString("year")));
                                        anime.setImage(jsonObject.getString("image"));
                                        anime.setOriginalname(jsonObject.getString("originalname"));
                                        anime.setRating(jsonObject.getString("rating"));
                                        anime.setDemography(jsonObject.getString("demography"));
                                        anime.setGenre(jsonObject.getString("genre"));
                                        anime.setImage2(jsonObject.getString("image2"));
                                        anime.setImage3(jsonObject.getString("image3"));
                                        anime.setActive(Integer.parseInt(jsonObject.getString("active")));
                                        anime.setFavorito(Boolean.parseBoolean(jsonObject.getString("favorito")));
                                        Log.d("siiiiii: ", anime.toString());
                                        animes.add(anime);
                                    }catch (JSONException e){
                                        throw new RuntimeException(e);
                                    }
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(AnimeFragment.this.getActivity()));
                                adapterAnime = new AdapterAnime(getActivity(), animes);
                                recyclerView.setAdapter(adapterAnime);
                                progressBarAnime.setVisibility(View.INVISIBLE);

                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error", "Error en la petición: " + error.toString());
                                if (error.networkResponse != null) {
                                    Log.e("Volley Error", "Código de respuesta HTTP: " + error.networkResponse.statusCode);
                                }
                                Toast.makeText(AnimeFragment.this.getContext(), "Error read animes", Toast.LENGTH_LONG).show();
                            }
                        }
                );
                queue.add(jsonArrayRequest);
            }
        }, 1500);

    }

    private int obtenerUserID(){
        SharedPreferences sharedPreferences = AnimeFragment.this.requireContext().getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }
}