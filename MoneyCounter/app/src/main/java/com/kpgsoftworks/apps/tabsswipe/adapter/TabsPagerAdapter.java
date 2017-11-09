package com.kpgsoftworks.apps.tabsswipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import layout.ChecksFragment;
import layout.CoinsFragment;
import layout.NotesFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mNumOfTabs = 3;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Notes fragment activity
                return new NotesFragment();
            case 1:
                // Coins fragment activity
                return new CoinsFragment();
            case 2:
                // Checks fragment activity
                return new ChecksFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return mNumOfTabs;
    }

}
