package com.example.markonni.comtradesuperheroes.fragments.Series;

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

    public void setItems(List<Serie> items) {
        this.serieList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_list_fragment_tv_shows_picture1);
        }
    }

    public SerieAdapter(List<Serie> serieList) {
        this.serieList = serieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.series_design, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Serie series = serieList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(series.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {return serieList.size();}

}
