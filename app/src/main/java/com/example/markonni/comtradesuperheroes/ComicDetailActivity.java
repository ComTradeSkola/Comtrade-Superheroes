package com.example.markonni.comtradesuperheroes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ComicDetailActivity extends AppCompatActivity {

    private String TAG = ComicDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic_details_activity);

        int comicId;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            comicId = extras.getInt("comicId");
            Log.d(TAG, "comicId: " + comicId);
        }
    }
}
