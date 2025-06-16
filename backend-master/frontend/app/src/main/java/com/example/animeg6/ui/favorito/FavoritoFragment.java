package com.example.animeg6.ui.favorito;

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
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.animeg6.databinding.FragmentFavoritoBinding;
import com.example.animeg6.model.Anime;
import com.example.animeg6.ui.anime.AnimeFragment;
import com.example.animeg6.utils.MyFavoritosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FavoritoFragment extends Fragment {

    public static final String IP = "10.200.243.205";
    private static final String URl_PART1 = "http://"+IP+":8091/user/";
    private static int ID_USER = 1;
    private static final String URl_PART2 = "/favoritos";

    private FragmentFavoritoBinding binding;

    private List<Anime> animes = new ArrayList<>();
    private RecyclerView recyclerViewFavoritos;

    private ProgressBar progressBarFavorito;
    private MyFavoritosAdapter myFavoritosAdapter;
    private LinearLayoutManager llm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritoViewModel favoritoViewModel =
                new ViewModelProvider(this).get(FavoritoViewModel.class);

        binding = FragmentFavoritoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBarFavorito = binding.progressBarFavorito;

        recyclerViewFavoritos = binding.recyclerViewFavoritos;
        recyclerViewFavoritos.setVisibility(View.INVISIBLE);
        llm = new LinearLayoutManager(FavoritoFragment.this.getActivity());

        getAnimesFavArray();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getAnimesFavArray() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ID_USER = obtenerUserID();
                animes = new ArrayList<>();
                RequestQueue queue = Volley.newRequestQueue(FavoritoFragment.this.requireContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,

                        URl_PART1 + ID_USER + URl_PART2,

                        null,

                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("EFRS: ", "FUNCIONAAAAAAAAAA");
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        Anime a = new Anime();

                                        a.setId(jsonObject.getInt("id"));
                                        a.setName(jsonObject.getString("name"));
                                        a.setDescription(jsonObject.getString("description"));
                                        a.setType(jsonObject.getString("type"));
                                        a.setYear(jsonObject.getInt("year"));
                                        a.setImage(jsonObject.getString("image"));
                                        a.setOriginalname(jsonObject.getString("originalname"));
                                        a.setRating(jsonObject.getString("rating"));
                                        a.setDemography(jsonObject.getString("demography"));
                                        a.setGenre(jsonObject.getString("genre"));
                                        a.setImage2(jsonObject.getString("image2"));
                                        a.setImage3(jsonObject.getString("image3"));
                                        a.setActive(jsonObject.getInt("active"));
                                        a.setFavorito(jsonObject.getBoolean("favorito"));

                                        animes.add(a);

                                        Log.d("EFRS: ", a.toString());

                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                                myFavoritosAdapter = new MyFavoritosAdapter(animes, getActivity());
                                recyclerViewFavoritos.setLayoutManager(llm);
                                recyclerViewFavoritos.setAdapter(myFavoritosAdapter);
                                progressBarFavorito.setVisibility(View.INVISIBLE);

                                recyclerViewFavoritos.setVisibility(View.VISIBLE);
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FavoritoFragment.this.getContext(), "Error reading JSON :·[", Toast.LENGTH_SHORT).show();
                                Log.d("EFRS: ", Arrays.toString(error.getStackTrace()));
                            }
                        }
                );

                queue.add(jsonArrayRequest);
            }
        }, 1500);
    }

    private int obtenerUserID(){
        SharedPreferences sharedPreferences = FavoritoFragment.this.requireContext().getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }
}