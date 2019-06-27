package com.codearms.maoqiqi.one.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public abstract class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected List<String> fragmentTitles;

    public SectionsPagerAdapter(List<String> fragmentTitles, FragmentManager fm) {
        super(fm);
        this.fragmentTitles = fragmentTitles;
    }

    @Override
    public int getCount() {
        return fragmentTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}