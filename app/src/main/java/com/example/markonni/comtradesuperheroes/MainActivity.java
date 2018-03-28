package com.example.markonni.comtradesuperheroes;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.superhero.Superhero;
import com.example.markonni.comtradesuperheroes.superhero.SuperheroAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadCallback{

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private NetworkFragment mNetworkFragment;

    private List<Superhero> superheroList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SuperheroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new SuperheroAdapter(superheroList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        String url = new GeneratingHash().getCharactersUrl();
        Log.d(TAG, "url: " + url);
        if (url != null) {
            mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), url);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNetworkFragment.startDownload();
    }

    @Override
    public void updateFromDownload(String result) {
        Log.d(TAG, result);
        new GetSuperheroes().execute(result);
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
        Log.d(TAG, "progress code: " + progressCode + " percentComplete " + percentComplete);
    }

    @Override
    public void finishDownloading() {

    }


    private class GetSuperheroes extends AsyncTask<String, Void, List<Superhero>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Superhero> doInBackground(String... arg0) {
            String result = arg0[0];
            if (result != null) {
                try {
                    List<Superhero> listOfHeros = new ArrayList<>();

                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject dataObject = jsonObj.getJSONObject("data");

                    JSONArray superheroes = dataObject.getJSONArray("results");

                    for (int i = 0; i < superheroes.length(); i++) {

                        Superhero superhero = new Superhero();

                        JSONObject c = superheroes.getJSONObject(i);

                        String name = c.getString("name");
                        String description = c.getString("description");

                        JSONObject thumbnail = c.getJSONObject("thumbnail");
                        String path = thumbnail.getString("path");
                        String extension = thumbnail.getString("extension");

                        superhero.setSuperheroName(name);
                        superhero.setDescription(description);
                        superhero.setImage(path + "/landscape_amazing." + extension);

                        Log.d(TAG, "Superheroj: " + superhero);

                        listOfHeros.add(superhero);

                    }
                    return listOfHeros;
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
        protected void onPostExecute(List<Superhero> result) {
            super.onPostExecute(result);
            mAdapter.setItems(result);
        }
    }
}