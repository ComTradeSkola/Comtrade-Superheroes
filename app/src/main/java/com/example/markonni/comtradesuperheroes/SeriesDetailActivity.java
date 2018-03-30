package com.example.markonni.comtradesuperheroes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.markonni.comtradesuperheroes.fragments.series.Serie;

public class SeriesDetailActivity extends AppCompatActivity {

    private String TAG = SeriesDetailActivity.class.getSimpleName();

    private TextView textViewTitleSeries;
    private TextView textViewDescriptionSeries;
    private ImageView imageViewSeries;
    private Serie serie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series_details_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            serie = extras.getParcelable("serieId");
            Log.d(TAG, "serieDetail: " + serie);
        }

        textViewTitleSeries = findViewById(R.id.text_view_series_details_activity_name);
        textViewDescriptionSeries = findViewById(R.id.text_view_series_details_activity_description);
        imageViewSeries = findViewById(R.id.image_view_series_details_activity);

        textViewTitleSeries.setText(serie.getTitle());
        textViewDescriptionSeries.setText(serie.getDescription());
        Glide.with(imageViewSeries.getContext())
                .load(serie.getSerieDetailImage())
                .into(imageViewSeries);
    }





}
