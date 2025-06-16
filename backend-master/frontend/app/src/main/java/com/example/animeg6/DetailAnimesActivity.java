package com.example.animeg6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.animeg6.model.Anime;
import com.example.animeg6.model.Video;
import com.example.animeg6.ui.favorito.FavoritoFragment;
import com.example.animeg6.utils.MyFavoritosAdapter;
import com.example.animeg6.utils.MyVideoAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailAnimesActivity extends AppCompatActivity {

    public static final String IP = "10.200.243.205";
    private static final String URl_PART1 = "http://"+IP+":8091/user/";
    private static int ID_USER;
    private static final String URl_PART2 = "/anime/";
    private static final String URl_PART3 = "/favoritos";

    private ImageView imageViewBackground;
    private ImageView imageViewForeground;
    private TextView textViewTipus;
    private ImageView imageViewNotFavorito;
    private ImageView imageViewFavorito;
    private TextView textViewTituloAnime;
    private TextView textViewNombreOriginal;
    private TextView textViewDemografia;
    private TextView textViewNombreOriginalOutput;
    private TextView textViewGenero;
    private TextView textViewAno;
    private TextView textViewPG;
    private TextView textViewGeneroOutput;
    private TextView textViewAnoOutput;
    private TextView textViewPGOutput;
    private TextView textViewSinopsis;
    private TextView textViewSinopsisOutput;
    private TextView testViewEpisodio;
    private RecyclerView recyclerViewVideos;

    private List<Video> videos;

    private Anime anime;


    private MyVideoAdapter myVideoAdapter;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_item_anime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraint_principal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        anime = (Anime) intent.getSerializableExtra("anime");
        if (anime == null) anime = Anime.getAnime(this);

        imageViewBackground = findViewById(R.id.imageViewBackground);
        imageViewForeground = findViewById(R.id.imageViewForeground);
        textViewTipus = findViewById(R.id.textViewTipus);
        textViewTituloAnime = findViewById(R.id.textViewTituloAnime);
        textViewNombreOriginal = findViewById(R.id.textViewNombreOriginal);
        textViewDemografia = findViewById(R.id.textViewDemografia);
        textViewNombreOriginalOutput = findViewById(R.id.textViewNombreOriginalOutput);
        textViewGenero = findViewById(R.id.textViewGenero);
        textViewAno = findViewById(R.id.textViewAno);
        textViewPG = findViewById(R.id.textViewPG);
        textViewGeneroOutput = findViewById(R.id.textViewGeneroOutput);
        textViewAnoOutput = findViewById(R.id.textViewAnoOutput);
        textViewPGOutput = findViewById(R.id.textViewPGOutput);
        textViewSinopsis = findViewById(R.id.textViewSinopsis);
        textViewSinopsisOutput = findViewById(R.id.textViewSinopsisOutput);
        testViewEpisodio = findViewById(R.id.testViewEpisodio);
        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);


        int tintColor = ContextCompat.getColor(this, R.color.orange);
        imageViewFavorito = findViewById(R.id.imageViewFavorito);
        imageViewNotFavorito = findViewById(R.id.imageViewNotFavorito);
        imageViewFavorito.setImageResource(R.drawable.ic_baseline_favorite_24);
        imageViewFavorito.setImageTintList(ColorStateList.valueOf(tintColor));
        imageViewNotFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        imageViewNotFavorito.setImageTintList(ColorStateList.valueOf(tintColor));

        if (anime.isFavorito()) {
            imageViewFavorito.setVisibility(View.VISIBLE);
            imageViewNotFavorito.setVisibility(View.INVISIBLE);
        } else {
            imageViewFavorito.setVisibility(View.INVISIBLE);
            imageViewNotFavorito.setVisibility(View.VISIBLE);
        }

        Picasso.get()
                .load(anime.getImage())
                .fit()
                .centerCrop()
                .into(imageViewBackground);
        Picasso.get()
                .load(anime.getImage2())
                .fit()
                .centerCrop()
                .into(imageViewForeground);
        textViewTipus.setText(anime.getType()+"");
        //fav
        //no fav
        textViewDemografia.setText(anime.getDemography()+"");
        textViewNombreOriginalOutput.setText(anime.getOriginalname()+"");
        textViewGeneroOutput.setText(anime.getGenre()+"");
        textViewAnoOutput.setText(anime.getYear()+"");
        textViewPGOutput.setText(anime.getRating()+"");
        textViewSinopsisOutput.setText(anime.getDescription()+"");

        llm = new LinearLayoutManager(this);
        recyclerViewVideos.setLayoutManager(llm);
        getVideos(this);


        imageViewNotFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewNotFavorito.setVisibility(View.INVISIBLE);
                imageViewFavorito.setVisibility(View.VISIBLE);

                addFavoritos(anime.getId());
            }
        });

        imageViewFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewFavorito.setVisibility(View.INVISIBLE);
                imageViewNotFavorito.setVisibility(View.VISIBLE);

                deleteFavoritos(anime.getId());
            }
        });

    }


    private void addFavoritos(int idAnime){
        ID_USER = obtenerUserID();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URl_PART1 + ID_USER + URl_PART2 + idAnime + URl_PART3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailAnimesActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailAnimesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

    }

    private void deleteFavoritos(int idAnime){
        ID_USER = obtenerUserID();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                URl_PART1 + ID_USER + URl_PART2 + idAnime + URl_PART3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailAnimesActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailAnimesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

    }

    public void getVideos(DetailAnimesActivity detailAnimesActivity) {
        String url = "http://"+IP+":8091/anime/"+anime.getId()+"/videos";
        videos = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("EFRS: ", "FUNCIONAAAAAAAAAA");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Video a = new Video();

                                a.setIdanime(jsonObject.getInt("idanime"));
                                a.setEpisode(jsonObject.getInt("episode"));
                                a.setUrl(jsonObject.getString("url"));
                                a.setImage(jsonObject.getString("image"));

                                videos.add(a);

                                Log.d("EFRS: ", a.toString());

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Initialize adapter only after data is received
                        myVideoAdapter = new MyVideoAdapter(videos, detailAnimesActivity);
                        recyclerViewVideos.setAdapter(myVideoAdapter); // Set the adapter after populating data
                        recyclerViewVideos.setLayoutManager(llm);

                        Log.d("EFRS: ", "Adapter set successfully!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailAnimesActivity.this, "Error reading JSON :·[", Toast.LENGTH_SHORT).show();
                        Log.d("EFRS: ", Arrays.toString(error.getStackTrace()));
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }

    private int obtenerUserID(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }
}