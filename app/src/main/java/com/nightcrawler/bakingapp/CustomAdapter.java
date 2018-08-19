package com.nightcrawler.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<String> list_members = new ArrayList<>();
    private final LayoutInflater inflater;
    private Context context;

    public CustomAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //resolutions-- "w92", "w154", "w185", "w342", "w500", "w780", or "original"

//        String base_image_url="https://image.tmdb.org/t/p/w500";
//        String poster_path=list_members.get(position).getPosterPath();
//        String url  =   base_image_url  +   poster_path;
//        Picasso.get().load(url).placeholder(R.drawable.ph).into(holder.picture);
        holder.dishName.setText(list_members.get(position));
    }

    public void setListContent(ArrayList<String> list_members) {
        this.list_members = list_members;
        notifyItemRangeChanged(0, list_members.size());

    }

    @Override
    public int getItemCount() {
        return list_members.size();
    }

    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dishName;

        //        private ImageView picture;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            dishName = itemView.findViewById(R.id.dishName);
//            movieName= itemView.findViewById(R.id.movieName);
        }

        @Override
        public void onClick(View v) {
            
            if(Utils.checkConnectivity(context)) {
                int pos = getAdapterPosition();
                Intent intent = new Intent(context, DetailsActivity.class);

                Bundle args = new Bundle();
                args.putInt("KEY", (pos+1));
                intent.putExtra("BUNDLE", args);
//                Bundle args = new Bundle();
//                args.putInt("KEY", pos);
//                intent.putExtra("BUNDLE", args);
                context.startActivity(intent);
            }
            else
                Toast.makeText(context, "Ensure data connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeAt(int position) {
        list_members.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, list_members.size());
    }

}