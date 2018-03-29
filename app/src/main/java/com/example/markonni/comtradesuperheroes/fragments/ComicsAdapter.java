package com.example.markonni.comtradesuperheroes.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.R;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.MyViewHolder> {

    private List<Comic> comicsList;

    public void setItems(List<Comic> items) {
        this.comicsList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_list_fragment_comics_picture1);
        }
    }

    public ComicsAdapter(List<Comic> comicsList) {
        this.comicsList = comicsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comic_design, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comic comics = comicsList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(comics.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }
}