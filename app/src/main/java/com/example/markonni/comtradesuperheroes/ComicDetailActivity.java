package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.fragments.comic.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComicDetailActivity extends AppCompatActivity {

    private String TAG = ComicDetailActivity.class.getSimpleName();
    private DownloadCallback downloadCallback;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageView;
    private Comic comic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic_details_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            comic = extras.getParcelable("comicId");
            Log.d(TAG, "comicDetail: " + comic);
        }

        textViewTitle = findViewById(R.id.text_view_comic_details_activity_title);
        textViewDescription = findViewById(R.id.text_view_comic_details_activity_description);
        imageView = findViewById(R.id.image_view_comic_details_activity_image);

        textViewTitle.setText(comic.getTitle());
        textViewDescription.setText(comic.getDescription());
        Glide.with(imageView.getContext())
                .load(comic.getImageComicDetail())
                .into(imageView);
    }

}
