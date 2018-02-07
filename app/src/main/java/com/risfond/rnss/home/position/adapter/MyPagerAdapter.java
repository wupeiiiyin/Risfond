package com.risfond.rnss.home.position.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tabNames;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabNames) {
        super(fm);
        this.fragments = fragments;
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int arg0) {
        return (fragments == null || fragments.size() == 0) ? null : fragments.get(arg0);
    }

    public void updateData(List<Fragment> fragments, List<String> tabNames){
        this.fragments = fragments;
        this.tabNames = tabNames;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
