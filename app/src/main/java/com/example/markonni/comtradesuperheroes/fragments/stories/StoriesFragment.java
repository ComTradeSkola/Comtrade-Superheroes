package com.example.markonni.comtradesuperheroes.fragments.stories;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markonni.comtradesuperheroes.R;

public class StoriesFragment extends Fragment {


    public StoriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment_stories, container, false);

        return rootView;
    }
}
