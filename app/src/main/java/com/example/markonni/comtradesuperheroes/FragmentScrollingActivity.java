package com.example.markonni.comtradesuperheroes;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.markonni.comtradesuperheroes.fragments.ComicsFragment;

public class FragmentScrollingActivity extends AppCompatActivity {


    private String TAG = FragmentScrollingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_item);

        ViewPager viewPager = findViewById(R.id.viewpager);

        int superheroId;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           superheroId = extras.getInt("superheroId");
            Log.d(TAG, "superheroID: " + superheroId);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), superheroId);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        }

    }



}

