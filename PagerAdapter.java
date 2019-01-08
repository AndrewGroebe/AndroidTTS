package com.example.groebe.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * A class that effectively manages the use of multiple fragments, or pages, in this application.
 *
 * @author Andrew Groebe.
 */
public final class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    PagerAdapter(FragmentManager manager, List<Fragment> fragments) {
        super(manager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
