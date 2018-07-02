package com.example.rahul.goodplays;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahul.goodplays.Fragments.ArtistsFragment;
import com.example.rahul.goodplays.Fragments.FavouriteFragment;
import com.example.rahul.goodplays.Fragments.TracksFragment;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TracksFragment();
        } else if (position == 1){
            return new ArtistsFragment();
        } else {
            return new FavouriteFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Tracks";
        } else if (position == 1){
            return "Artists";
        } else {
            return "Favourites";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
