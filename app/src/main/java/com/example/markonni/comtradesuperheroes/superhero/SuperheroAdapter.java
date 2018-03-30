package com.example.markonni.comtradesuperheroes.superhero;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.R;

import java.util.List;

public class SuperheroAdapter extends RecyclerView.Adapter<SuperheroAdapter.MyViewHolder> {

    private List<Superhero> superheroList;
    private OnSuperheroSelected onSuperheroSelected;

    public void setItems(List<Superhero> items) {
        this.superheroList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewName;
        private OnClickCallback onClickCallback;

        public MyViewHolder(View view, final OnClickCallback onClickCallback) {
            super(view);
            this.onClickCallback = onClickCallback;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onClickCallback != null) {
                            onClickCallback.onItemClick(position);
                        }
                    }
                }
            });
            imageView = view.findViewById(R.id.image_view_list_superhero_constraint_picture);
            textViewName = view.findViewById(R.id.text_view_list_superhero_constraint_name);
        }
    }

    public SuperheroAdapter(List<Superhero> superheroList, OnSuperheroSelected onSuperheroSelected) {
        this.superheroList = superheroList;
        this.onSuperheroSelected = onSuperheroSelected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_superhero_constraint,parent,false);

        return new MyViewHolder(itemView, new OnClickCallback() {
            @Override
            public void onItemClick(int position) {
                if (superheroList != null) {
                    Superhero superhero = superheroList.get(position);
                    if (onSuperheroSelected != null) {
                        onSuperheroSelected.onSuperheroSelected(superhero);
                    }
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Superhero superhero = superheroList.get(position);
        holder.textViewName.setText(superhero.getSuperheroName());
        Glide.with(holder.itemView.getContext())
                .load(superhero.getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return superheroList != null ? superheroList.size() : 0;
    }

    public interface OnSuperheroSelected {
        void onSuperheroSelected(Superhero superhero);
    }

    private interface OnClickCallback {
        void onItemClick(int position);
    }
}

