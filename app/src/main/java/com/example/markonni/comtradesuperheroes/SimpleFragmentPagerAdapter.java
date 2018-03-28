package com.example.markonni.comtradesuperheroes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.markonni.comtradesuperheroes.fragments.ComicsFragment;
import com.example.markonni.comtradesuperheroes.fragments.StoriesFragment;
import com.example.markonni.comtradesuperheroes.fragments.TVShowsFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ComicsFragment();
        } else if (position == 1){
            return new TVShowsFragment();
        } else if (position == 2){
            return new StoriesFragment();
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
