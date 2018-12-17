package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        RatingBar rating;
        TextView title;
        TextView date;
        TextView director;
        TextView actor;

        Holder(View view){
            super(view);
            this.image = (ImageView) view.findViewById(R.id.list_image);
            this.rating = (RatingBar) view.findViewById(R.id.list_rating);
            this.title = (TextView) view.findViewById(R.id.list_title);
            this.date = (TextView) view.findViewById(R.id.list_date);
            this.director = (TextView) view.findViewById(R.id.list_director);
            this.actor = (TextView) view.findViewById(R.id.list_actor);
        }
    }
    private List<Movie> movieList;
    Adapter(List<Movie> list){
        this.movieList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, final int position) {
        Holder holder = (Holder) viewholder;
        holder.title.setText(movieList.get(position).getTitle());
        holder.date.setText(""+movieList.get(position).getPubDate());
        holder.actor.setText(movieList.get(position).getActor());
        holder.director.setText(movieList.get(position).getDirector());
        holder.rating.setRating(movieList.get(position).getUserRating());
        holder.image.setImageBitmap(movieList.get(position).getImage());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(movieList.get(position).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
