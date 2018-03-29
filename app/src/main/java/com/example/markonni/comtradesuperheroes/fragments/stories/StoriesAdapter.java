package com.example.markonni.comtradesuperheroes.fragments.stories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.R;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    private List<Story> storyList;

    public void setItems(List<Story> items) {
        this.storyList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_list_fragment_stories_picture1);
        }
    }

    public StoriesAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.stories_design, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Story stories = storyList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(stories.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return  storyList.size();
    }

}
