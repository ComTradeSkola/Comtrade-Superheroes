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

    private List<Superhero> supperheroList;

    public void setItems(List<Superhero> items) {
        this.supperheroList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewDescription;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_list_superhero_constraint_picture);
            textViewName = view.findViewById(R.id.text_view_list_superhero_constraint_name);
            textViewDescription = view.findViewById(R.id.text_view_list_superhero_constraint_description);
        }
    }

    public SuperheroAdapter(List<Superhero> superheroList) {
        this.supperheroList = superheroList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_superhero_constraint,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Superhero superhero = supperheroList.get(position);
        holder.textViewName.setText(superhero.getSuperheroName());
        holder.textViewDescription.setText(superhero.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(superhero.getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount(){
        return supperheroList.size();
    }
}

