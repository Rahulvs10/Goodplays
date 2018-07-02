package com.example.rahul.goodplays;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahul.goodplays.Fragments.ArtistAlbumsFragment;
import com.example.rahul.goodplays.Fragments.ArtistTracksFragment;
import com.example.rahul.goodplays.Fragments.ArtistsFragment;
import com.example.rahul.goodplays.Fragments.FavouriteFragment;
import com.example.rahul.goodplays.Fragments.TracksFragment;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter2 extends FragmentPagerAdapter {

    private int ID;
    private String mName;

    public SimpleFragmentPagerAdapter2(FragmentManager fm, int id, String name) {
        super(fm);
        ID = id;
        mName = name;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ArtistTracksFragment(ID, mName);
        } else {
            return new ArtistAlbumsFragment(ID);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Tracks";
        } else {
            return "Albums";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
