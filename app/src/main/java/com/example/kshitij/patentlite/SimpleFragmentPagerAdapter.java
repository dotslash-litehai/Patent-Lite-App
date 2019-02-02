package com.example.kshitij.patentlite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabtitles[] = new String[]{"Applications","Status","New Patent"};

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
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
        } else if (position == 1){
            return new StatusFragment();
        } else {
            return new NewFormFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
