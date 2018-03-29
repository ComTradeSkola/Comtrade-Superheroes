package com.example.markonni.comtradesuperheroes.fragments.superhero_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markonni.comtradesuperheroes.R;

import java.util.List;

public class SuperheroDetailsAdapter extends RecyclerView.Adapter<SuperheroDetailsAdapter.MyViewHolder> {

    private List<SuperheroDetail> superheroDetailsList;

    public void setItems(List<SuperheroDetail> items) {
        this.superheroDetailsList = items;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;

        public MyViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.text_view_superhero_details_design_name);
            textViewDescription = view.findViewById(R.id.text_view_comic_details_activity_description);
        }
    }

    public SuperheroDetailsAdapter(List<SuperheroDetail> superheroDetailsList) {
        this.superheroDetailsList = superheroDetailsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.superhero_details_design,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SuperheroDetail superheroDetail = superheroDetailsList.get(position);
        holder.textViewName.setText(superheroDetail.getName());
        holder.textViewDescription.setText(superheroDetail.getDescription());
    }

    @Override
    public int getItemCount() {
        return superheroDetailsList.size();
    }
}
