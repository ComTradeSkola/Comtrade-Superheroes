package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.fragments.comic.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComicDetailActivity extends AppCompatActivity {

    private String TAG = ComicDetailActivity.class.getSimpleName();
    private DownloadCallback downloadCallback;
    private int comicId;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageView;
    private List<ComicDetails> comicDetails = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic_details_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            comicId = extras.getInt("comicId");
            Log.d(TAG, "comicId: " + comicId);
        }

        downloadCallback = new DownloadCallback() {
            @Override
            public void updateFromDownload(String result) {
                Log.d(TAG, "result json: " + result);
                new GetComicDetails().execute(result);
            }

            @Override
            public NetworkInfo getActiveNetworkInfo() {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

        imageView = findViewById(R.id.image_view_comic_details_activity_image);
    }


    @Override
    public void onStart() {
        super.onStart();

        String url = new GeneratingHash().getOneComicUrl(comicId);
        Log.d(TAG, "Comics url: " + url);

        new DownloadTask(downloadCallback).execute(url);
    }

    private class GetComicDetails extends AsyncTask<String, Void, List<ComicDetails>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<ComicDetails> doInBackground(String... arg0) {
            String result = arg0[0];
            if (result != null) {
                try {
                    List<ComicDetails> comicDetailsList = new ArrayList<>();

                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject dataObject = jsonObj.getJSONObject("data");

                    JSONArray comic = dataObject.getJSONArray("results");

                    for (int i = 0; i < comic.length(); i++) {

                        ComicDetails comicDetails = new ComicDetails();

                        JSONObject c = comic.getJSONObject(i);

                        String name = c.getString("title");
                        String description = c.getString("description");

                        JSONObject thumbnail = c.getJSONObject("thumbnail");
                        String path = thumbnail.getString("path");
                        String extension = thumbnail.getString("extension");

                        comicDetails.setTitle(name);
                        comicDetails.setDescription(description);
                        comicDetails.setImage(path + "/portrait_xlarge." + extension);

                        Log.d(TAG, "Comic detail: " + comicDetails);

                        comicDetailsList.add(comicDetails);

                    }
                    return comicDetailsList;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ComicDetails> result) {
            super.onPostExecute(result);
        }
    }
}
