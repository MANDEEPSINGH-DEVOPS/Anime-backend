package com.example.animeg6.utils;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.animeg6.DetailAnimesActivity;
import com.example.animeg6.R;
import com.example.animeg6.model.Anime;
import com.example.animeg6.ui.favorito.FavoritoFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class MyFavoritosAdapter extends RecyclerView.Adapter<MyFavoritosAdapter.MyViewHolder> {

    public static final String IP = "10.200.243.205";
    private static final String URl_PART1 = "http://"+IP+":8091/user/";
    private static int ID_USER = 1;
    private static final String URl_PART2 = "/anime/";
    private static final String URl_PART3 = "/favoritos";

    private List<Anime> animes;
    private FragmentActivity context;

    private Fragment detailFragment;

    public MyFavoritosAdapter(List<Anime> animes, FragmentActivity context) {
        this.animes = animes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyFavoritosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.anime_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavoritosAdapter.MyViewHolder holder, int position) {
        Picasso.get()
                .load(animes.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.fotoAnime);

        holder.tituloAnime.setText(animes.get(position).getName());
        holder.descripcionAnime.setText(animes.get(position).getDescription());
        holder.yearAnime.setText(animes.get(position).getYear()+"");
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

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAnimesActivity.class);
                intent.putExtra("anime", animes.get(holder.getAdapterPosition()));
                Anime.saveAnime(context, animes.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.imageFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageFavorito.setVisibility(View.INVISIBLE);
                holder.imageNoFavorito.setVisibility(View.VISIBLE);

                deleteFavoritos(animes.get(holder.getAdapterPosition()).getId());

                animes.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoAnime;
        private TextView tituloAnime;
        private TextView descripcionAnime;
        private TextView yearAnime;
        private ImageView imageFavorito;
        private ImageView imageNoFavorito;

        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoAnime = itemView.findViewById(R.id.fotoAnime);
            tituloAnime = itemView.findViewById(R.id.tituloAnime);
            descripcionAnime = itemView.findViewById(R.id.descripcionAnime);
            yearAnime = itemView.findViewById(R.id.yearAnime);
            imageFavorito = itemView.findViewById(R.id.imageFavorito);
            imageNoFavorito = itemView.findViewById(R.id.imageNoFavorito);

            constraintLayout = itemView.findViewById(R.id.my_row_anime);
        }
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
