package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.markonni.comtradesuperheroes.fragments.comic.ComicsFragment;
import com.example.markonni.comtradesuperheroes.fragments.series.SeriesFragment;
import com.example.markonni.comtradesuperheroes.fragments.superhero_details.SuperheroDetailsFragment;
import com.example.markonni.comtradesuperheroes.superhero.Superhero;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Superhero superhero;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, Superhero superhero) {
        super(fm);
        mContext = context;
        this.superhero = superhero;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ComicsFragment.newInstance(superhero.getSuperheroId());
        } else if (position == 1){
            return SeriesFragment.newInstance(superhero.getSuperheroId());
        } else if (position == 2){
            return SuperheroDetailsFragment.newInstance(superhero);
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
