package com.example.markonni.comtradesuperheroes.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {


    public ComicsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int superHeroId) {
        //TODO send data to the fragment
        return new ComicsFragment();
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
                //TODO isto kao i funkcija iz main activija
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
        //TODO ovde iz bundla izvuci superHeroId, i na osnovu toga startovati task da krene downlaod stripova
        //String url = new GeneratingHash().getCommicsUrl(superHeroId);
        new DownloadTask(downloadCallback).execute();
    }
}