package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.markonni.comtradesuperheroes.fragments.comic.ComicsFragment;
import com.example.markonni.comtradesuperheroes.fragments.series.SeriesFragment;
import com.example.markonni.comtradesuperheroes.fragments.superhero_details.SuperheroDetailsFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int superheroId;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, int superheroId) {
        super(fm);
        mContext = context;
        this.superheroId = superheroId;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ComicsFragment.newInstance(superheroId);
        } else if (position == 1){
            return SeriesFragment.newInstance(superheroId);
        } else if (position == 2){
            return SuperheroDetailsFragment.newInstance(superheroId);
        } else {
            throw new IndexOutOfBoundsException("We don't have more then 3 pages");
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.comics_name);
            case 1:
                return mContext.getString(R.string.tv_shows_name);
            case 2:
                return mContext.getString(R.string.stories_name);
            default:
                return null;
        }
    }
}
