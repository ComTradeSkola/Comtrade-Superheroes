package com.example.markonni.comtradesuperheroes.fragments.superhero_details;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.R;
import com.example.markonni.comtradesuperheroes.superhero.Superhero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuperheroDetailsFragment extends Fragment {

    String TAG = SuperheroDetailsFragment.class.getSimpleName();
    private TextView textviewName;
    private TextView description;
    private Superhero superhero;


    public SuperheroDetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Superhero superheroId) {
        Fragment fragment = new SuperheroDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("superhero", superheroId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        superhero = getArguments().getParcelable("superhero");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.superhero_details_design, container, false);
        textviewName = rootView.findViewById(R.id.text_view_superhero_details_design_name);
        description = rootView.findViewById(R.id.text_view_superhero_details_design_description);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textviewName.setText(superhero.getSuperheroName());
        description.setText(superhero.getDescription());
    }
}
