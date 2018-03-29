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
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuperheroDetailsFragment extends Fragment {

    String TAG = SuperheroDetailsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private SuperheroDetailsAdapter superheroDetailsAdapter;
    private DownloadCallback downloadCallback;
    List<SuperheroDetail> superheroDetailList = new ArrayList<>();


    public SuperheroDetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int superheroId) {
        Fragment fragment = new SuperheroDetailsFragment();

        Bundle args = new Bundle();
        args.putInt("superheroId", superheroId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloadCallback = new DownloadCallback() {
            @Override
            public void updateFromDownload(String result) {
                Log.d(TAG, "result json: " + result);
                new GetSuperheroDetail().execute(result);
            }

            @Override
            public NetworkInfo getActiveNetworkInfo() {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo;
            }

            @Override
            public void onProgressUpdate(int progressCode, int percentComplete) {

            }

            @Override
            public void finishDownloading() {

            }
        };
        superheroDetailsAdapter = new SuperheroDetailsAdapter(superheroDetailList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.superhero_details_design, container, false);


//        recyclerView = rootView.findViewById(R.id.recycler_view_superhero_details);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(superheroDetailsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        int superheroId = getArguments().getInt("superheroId");

        String url = new GeneratingHash().getOneCharacterUrl(superheroId);
        Log.d(TAG, "Character detail url: " + url);

        new DownloadTask(downloadCallback).execute(url);
    }





    private class GetSuperheroDetail extends AsyncTask<String, Void, List<SuperheroDetail>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<SuperheroDetail> doInBackground(String... arg0) {
            String result = arg0[0];
            if (result != null) {
                try {
                    List<SuperheroDetail> listOfSuperheroDetails = new ArrayList<>();

                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject dataObject = jsonObj.getJSONObject("data");

                    JSONArray superheroes = dataObject.getJSONArray("results");

                    for (int i = 0; i < superheroes.length(); i++) {

                        SuperheroDetail superhero = new SuperheroDetail();

                        JSONObject c = superheroes.getJSONObject(i);

                        String name = c.getString("name");
                        String description = c.getString("description");

                        superhero.setName(name);
                        superhero.setDescription(description);

                        Log.d(TAG, "Superhero detail: " + superhero);

                        listOfSuperheroDetails.add(superhero);

                    }
                    return listOfSuperheroDetails;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SuperheroDetail> result) {
            super.onPostExecute(result);
            superheroDetailsAdapter.setItems(result);
        }
    }


}
