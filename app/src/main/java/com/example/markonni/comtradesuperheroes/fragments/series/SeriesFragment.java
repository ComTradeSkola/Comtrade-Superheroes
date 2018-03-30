package com.example.markonni.comtradesuperheroes.fragments.series;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.R;
import com.example.markonni.comtradesuperheroes.SeriesDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {

    String TAG = SeriesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private SerieAdapter seriesAdapter;
    private DownloadCallback downloadCallback;
    List<Serie> serieList = new ArrayList<>();


    public SeriesFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int superheroId) {
        Fragment fragment = new SeriesFragment();

        Bundle args = new Bundle();
        args.putInt("superheroId", superheroId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        seriesAdapter = new SerieAdapter(serieList, new SerieAdapter.OnSerieSelected() {
            @Override
            public void onSerieSelected(Serie serie) {
                serieSelected(serie);
            }
        });

        downloadCallback = new DownloadCallback() {
            @Override
            public void updateFromDownload(String result) {
                Log.d(TAG, "result json: " + result);
                new GetSeries().execute(result);
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
    }

    private void serieSelected(Serie serie) {

        Intent intent = new Intent(getActivity().getBaseContext(), SeriesDetailActivity.class);
        intent.putExtra("serieId", serie);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment_series, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_series);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(seriesAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        int superheroId = getArguments().getInt("superheroId");

        String url = new GeneratingHash().getSeriesUrl(superheroId);
        Log.d(TAG, "Comics url: " + url);

        new DownloadTask(downloadCallback).execute(url);
    }

    private class GetSeries extends AsyncTask<String, Void, List<Serie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Serie> doInBackground(String... arg0) {
            String result = arg0[0];
            if (result != null) {
                try {
                    List<Serie> listOfComics = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray comics = dataObject.getJSONArray("results");

                    for (int i = 0; i < comics.length(); i++) {

                        Serie serie = new Serie();

                        JSONObject c = comics.getJSONObject(i);
                        int serieId = c.getInt("id");
                        String title = c.getString("title");
                        String description = c.getString("description");

                        JSONObject thumbnail = c.getJSONObject("thumbnail");
                        String path = thumbnail.getString("path");
                        String extension = thumbnail.getString("extension");

                        serie.setSerieId(serieId);
                        serie.setDescription(description);
                        serie.setTitle(title);
                        serie.setImage(path + "/standard_medium." + extension);
                        serie.setSerieDetailImage(path + "/portrait_fantastic." +extension);

                        Log.d(TAG,"series: " + serie);

                        listOfComics.add(serie);
                    }
                    return listOfComics;
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
        protected void onPostExecute(List<Serie> result) {
            super.onPostExecute(result);
            seriesAdapter.setItems(result);
        }
    }

}
