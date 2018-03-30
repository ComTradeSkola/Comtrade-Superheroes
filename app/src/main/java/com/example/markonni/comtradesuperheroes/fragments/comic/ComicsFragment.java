package com.example.markonni.comtradesuperheroes.fragments.comic;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.ComicDetailActivity;
import com.example.markonni.comtradesuperheroes.DownloadCallback;
import com.example.markonni.comtradesuperheroes.DownloadTask;
import com.example.markonni.comtradesuperheroes.GeneratingHash;
import com.example.markonni.comtradesuperheroes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    String TAG = ComicsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView noDataTextView;
    private ProgressBar progressBar;
    private ComicsAdapter comicsAdapter;
    private DownloadCallback downloadCallback;
    List<Comic> comicList = new ArrayList<>();

    public ComicsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int superheroId) {

        Fragment fragment = new ComicsFragment();

        Bundle args = new Bundle();
        args.putInt("superheroId", superheroId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        comicsAdapter = new ComicsAdapter(comicList, new ComicsAdapter.OnComicSelected() {
            @Override
            public void onComicSelected(Comic comic) {
                comicSelected(comic);
            }
        });

        downloadCallback = new DownloadCallback() {
            @Override
            public void updateFromDownload(String result) {
                Log.d(TAG, "result json: " + result);
                new GetComics().execute(result);
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
        comicsAdapter = new ComicsAdapter(comicList, new ComicsAdapter.OnComicSelected() {
            @Override
            public void onComicSelected(Comic comic) {
                comicSelected(comic);
            }
        });
    }

    private void comicSelected(Comic comic) {

        Intent intent = new Intent(getActivity().getBaseContext(), ComicDetailActivity.class);
        intent.putExtra("comicId", comic);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment_comics, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_comics);
        noDataTextView = rootView.findViewById(R.id.no_data_text_view);
        progressBar = rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(comicsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        int superheroId = getArguments().getInt("superheroId");

        String url = new GeneratingHash().getComicsUrl(superheroId);
        Log.d(TAG, "Comics url: " + url);

        new DownloadTask(downloadCallback).execute(url);
    }

    private class GetComics extends AsyncTask<String, Void, List<Comic>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTextView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<Comic> doInBackground(String... arg0) {
            String result = arg0[0];
            if (result != null) {
                try {
                    List<Comic> listOfComics = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray comics = dataObject.getJSONArray("results");

                    for (int i = 0; i < comics.length(); i++) {

                        Comic comic = new Comic();

                        JSONObject c = comics.getJSONObject(i);

                        int comicId = c.getInt("id");
                        String title = c.getString("title");
                        String description = c.getString("description");

                        JSONObject thumbnail = c.getJSONObject("thumbnail");
                        String path = thumbnail.getString("path");
                        String extension = thumbnail.getString("extension");

                        comic.setTitle(title);
                        comic.setDescription(description);
                        comic.setImage(path + "/standard_medium." + extension);
                        comic.setImageComicDetail(path + "/portrait_fantastic." + extension);
                        comic.setComicId(comicId);

                        Log.d(TAG,"comics: " + comic);

                        listOfComics.add(comic);
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
        protected void onPostExecute(List<Comic> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            if (result.isEmpty()) {
                noDataTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                noDataTextView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                comicsAdapter.setItems(result);
            }
        }
    }
}