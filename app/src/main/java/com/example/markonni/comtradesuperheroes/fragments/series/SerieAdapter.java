package com.example.markonni.comtradesuperheroes.fragments.series;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.R;

import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.MyViewHolder> {

    private List<Serie> serieList;
    private OnSerieSelected onSerieSelected;

    public void setItems(List<Serie> items) {
        this.serieList = items;
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
            imageView = view.findViewById(R.id.image_view_list_fragment_tv_shows_picture1);
        }
    }

    public SerieAdapter(List<Serie> serieList, OnSerieSelected onSerieSelected) {
        this.serieList = serieList;
        this.onSerieSelected = onSerieSelected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.series_design, parent,false);

        return new MyViewHolder(itemView, new OnClickCallback() {
            @Override
            public void onItemClick(int position) {
                if (serieList != null) {
                    Serie serie = serieList.get(position);
                    if(onSerieSelected != null) {
                        onSerieSelected.onSerieSelected(serie);
                    }
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Serie series = serieList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(series.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {return serieList != null ? serieList.size() : 0;}

    public interface OnSerieSelected {
        void onSerieSelected(Serie serie);
    }

    private interface OnClickCallback {
        void onItemClick(int position);
    }

}
