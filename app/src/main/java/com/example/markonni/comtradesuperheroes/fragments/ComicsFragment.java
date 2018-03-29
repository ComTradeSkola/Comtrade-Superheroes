package com.example.markonni.comtradesuperheroes.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.FragmentScrollingActivity;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.MainActivity;
import com.example.markonni.comtradesuperheroes.NetworkFragment;
import com.example.markonni.comtradesuperheroes.R;
import com.example.markonni.comtradesuperheroes.SimpleFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    String TAG = ComicsFragment.class.getSimpleName();

    public ComicsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int superheroId) {

        Fragment fragment = new ComicsFragment();

        Bundle args = new Bundle();
        args.putInt("superheroId",superheroId);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private ComicsAdapter comicsAdapter;
    private DownloadCallback downloadCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        downloadCallback = new DownloadCallback() {
            @Override
            public void updateFromDownload(String result) {
                //TODO ovde opaliti drugi task koji ce da parsira json
            }

            @Override
            public NetworkInfo getActiveNetworkInfo() {
//                TODO isto kao i funkcija iz main activija
//                ConnectivityManager connectivityManager =
//                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//                return networkInfo;
                return null;
            }

            @Override
            public void onProgressUpdate(int progressCode, int percentComplete) {

            }

            @Override
            public void finishDownloading() {

            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment_comics, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        int superheroId = getArguments().getInt("superheroId");
        
        String url = new GeneratingHash().getComicsUrl(superheroId);
        Log.d(TAG, "Comics url: " + url);

        new DownloadTask(downloadCallback).execute();
    }
}