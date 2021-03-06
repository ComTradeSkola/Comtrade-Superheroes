package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markonni.comtradesuperheroes.superhero.Superhero;
import com.example.markonni.comtradesuperheroes.superhero.SuperheroAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadCallback {

    private String TAG = MainActivity.class.getSimpleName();

    private NetworkFragment mNetworkFragment;

    private List<Superhero> superheroList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SuperheroAdapter mAdapter;
    private TextView noDataTextViewMainActivity;
    private ProgressBar progressBarMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        noDataTextViewMainActivity = findViewById(R.id.no_data_text_view_main_activity);
        progressBarMainActivity = findViewById(R.id.progressBar_main_activity);

        mAdapter = new SuperheroAdapter(superheroList, new SuperheroAdapter.OnSuperheroSelected() {
            @Override
            public void onSuperheroSelected(Superhero superhero) {
                heroSelected(superhero);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), null);

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                runAHeroSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return true;
    }

    private void runAHeroSearch(String query) {
        if (query != null && !query.isEmpty()) {
            String url = new GeneratingHash().getCharactersUrl(query);
            Log.d(TAG, "url: " + url);
            mNetworkFragment.startDownload(url);
        }
    }

    private void heroSelected(Superhero superhero) {

        Intent intent = new Intent(getBaseContext(), FragmentScrollingActivity.class);
        intent.putExtra("superheroId", superhero);
        startActivity(intent);
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
            recyclerView.setVisibility(View.INVISIBLE);
            noDataTextViewMainActivity.setVisibility(View.INVISIBLE);
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
                        int superheroId = c.getInt("id");

                        JSONObject thumbnail = c.getJSONObject("thumbnail");
                        String path = thumbnail.getString("path");
                        String extension = thumbnail.getString("extension");

                        superhero.setSuperheroName(name);
                        superhero.setDescription(description);
                        superhero.setSuperheroId(superheroId);
                        superhero.setImage(path + "/landscape_amazing." + extension);
                        superhero.setImageSuperheroDetails(path + "/portrait_uncanny." + extension);

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
            progressBarMainActivity.setVisibility(View.INVISIBLE);
            if (result.isEmpty()) {
                noDataTextViewMainActivity.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                noDataTextViewMainActivity.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                mAdapter.setItems(result);
            }
        }
    }
}