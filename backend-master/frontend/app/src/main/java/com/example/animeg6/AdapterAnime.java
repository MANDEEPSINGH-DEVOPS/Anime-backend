package com.example.animeg6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.animeg6.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAnime extends RecyclerView.Adapter<AdapterAnime.AnimeViewHolder> {
    private Context context;
    private List<Anime> animes;

    public static final String IP = "10.200.243.205";
    private static final String URl_PART1 = "http://"+IP+":8091/user/";
    private static int ID_USER;
    private static final String URl_PART2 = "/anime/";
    private static final String URl_PART3 = "/favoritos";

    public AdapterAnime(Context context, List<Anime> animes) {
        this.context = context;
        this.animes = animes;
    }

    @NonNull
    @Override
    public AdapterAnime.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.anime_layout, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnime.AnimeViewHolder holder, int position) {
        Picasso.get().load(animes.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.fotoAnime);
        holder.tituloAnime.setText(animes.get(position).getName());
        holder.descripcionAnime.setText(animes.get(position).getDescription());
        holder.yearAnime.setText(String.valueOf(animes.get(position).getYear()));
        int tintColor = ContextCompat.getColor(context, R.color.orange);
        holder.imageFavorito.setImageResource(R.drawable.ic_baseline_favorite_24);
        holder.imageFavorito.setImageTintList(ColorStateList.valueOf(tintColor));
        holder.imageNoFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        holder.imageNoFavorito.setImageTintList(ColorStateList.valueOf(tintColor));
        if (animes.get(position).isFavorito()) {
            holder.imageFavorito.setVisibility(View.VISIBLE);
            holder.imageNoFavorito.setVisibility(View.INVISIBLE);
        } else {
            holder.imageFavorito.setVisibility(View.INVISIBLE);
            holder.imageNoFavorito.setVisibility(View.VISIBLE);
        }
        holder.my_row_anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAnimesActivity.class);
                intent.putExtra("anime", animes.get(holder.getAdapterPosition()));
                Anime.saveAnime(context, animes.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.imageNoFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageNoFavorito.setVisibility(View.INVISIBLE);
                holder.imageFavorito.setVisibility(View.VISIBLE);

                addFavoritos(animes.get(holder.getAdapterPosition()).getId());
            }
        });

        holder.imageFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageFavorito.setVisibility(View.INVISIBLE);
                holder.imageNoFavorito.setVisibility(View.VISIBLE);

                deleteFavoritos(animes.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoAnime;
        TextView tituloAnime;
        TextView descripcionAnime;
        TextView yearAnime;
        ImageView imageFavorito;
        ImageView imageNoFavorito;
        ConstraintLayout my_row_anime;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoAnime = itemView.findViewById(R.id.fotoAnime);
            tituloAnime = itemView.findViewById(R.id.tituloAnime);
            descripcionAnime = itemView.findViewById(R.id.descripcionAnime);
            yearAnime = itemView.findViewById(R.id.yearAnime);
            imageFavorito = itemView.findViewById(R.id.imageFavorito);
            imageNoFavorito = itemView.findViewById(R.id.imageNoFavorito);
            my_row_anime = itemView.findViewById(R.id.my_row_anime);
        }
    }

    private void addFavoritos(int idAnime){
        ID_USER = obtenerUserID();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URl_PART1 + ID_USER + URl_PART2 + idAnime + URl_PART3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

    }

    private void deleteFavoritos(int idAnime){
        ID_USER = obtenerUserID();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                URl_PART1 + ID_USER + URl_PART2 + idAnime + URl_PART3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

    }

    private int obtenerUserID(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AnimeG6Prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }

}
