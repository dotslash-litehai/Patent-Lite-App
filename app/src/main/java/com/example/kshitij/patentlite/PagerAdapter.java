package com.example.kshitij.patentlite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[];

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ApplicationFragment();
        } else {
            return new StatusFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
