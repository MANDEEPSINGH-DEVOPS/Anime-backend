package com.example.animeg6.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeg6.DetailAnimesActivity;
import com.example.animeg6.R;
import com.example.animeg6.VideoActivity;
import com.example.animeg6.model.Anime;
import com.example.animeg6.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.MyViewHolder> {

    private List<Video> videos;
    private FragmentActivity context;

    public MyVideoAdapter(List<Video> videos, FragmentActivity context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyVideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_row_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideoAdapter.MyViewHolder holder, int position) {
        Picasso.get()
                .load(videos.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.imageViewVideo);

        holder.textViewVideo.setText("Episodio "+videos.get(position).getEpisode());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video", videos.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewVideo;
        private TextView textViewVideo;

        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.video_row);

            imageViewVideo = itemView.findViewById(R.id.imageViewVideo);
            textViewVideo = itemView.findViewById(R.id.textViewVideo);

        }
    }
}
