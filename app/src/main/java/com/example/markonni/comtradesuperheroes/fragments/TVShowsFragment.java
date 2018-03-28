package com.example.markonni.comtradesuperheroes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markonni.comtradesuperheroes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment {


    public TVShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment_tv_shows, container, false);

        return rootView;
    }

}
