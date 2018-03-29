package com.example.markonni.comtradesuperheroes.fragments.comic;

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
    private OnComicSelected onComicSelected;

    public void setItems(List<Comic> items) {
        this.comicsList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private OnClickCallback onClickCallback;

        public MyViewHolder(View view, final OnClickCallback onClickCallback) {
            super(view);
            this.onClickCallback = onClickCallback;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if(onClickCallback != null) {
                            onClickCallback.onItemClick(position);
                        }
                    }
                }
            });
            imageView = view.findViewById(R.id.image_view_list_fragment_comics_picture1);
        }
    }

    public ComicsAdapter(List<Comic> comicsList, OnComicSelected onComicSelected) {
        this.comicsList = comicsList;
        this.onComicSelected = onComicSelected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comic_design, parent,false);

        return new MyViewHolder(itemView, new OnClickCallback() {
            @Override
            public void onItemClick(int position) {
                if(comicsList != null) {
                    Comic comic = comicsList.get(position);
                    if(onComicSelected != null) {
                        onComicSelected.onComicSelected(comic);
                    }
                }
            }
        });
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

    public interface OnComicSelected {
        void onComicSelected(Comic comic);
    }

    private interface OnClickCallback {
        void onItemClick(int position);
    }
}