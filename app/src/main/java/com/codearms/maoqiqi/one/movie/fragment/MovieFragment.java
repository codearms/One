package com.codearms.maoqiqi.one.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.MainActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.Arrays;
import java.util.List;

/**
 * 影视
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-27 10:15
 */
public class MovieFragment extends BaseFragment {

    private Toolbar toolbar;
    private SegmentTabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragment.
     */
    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        toolbar = rootView.findViewById(R.id.toolbar);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        if (savedInstanceState != null) {
            loadData();
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        ((MainActivity) context).associateDrawerLayout(toolbar);

        String[] arr = getResources().getStringArray(R.array.movie_titles);
        viewPager.setAdapter(new MyPagerAdapter(Arrays.asList(arr), getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(arr.length - 1);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());

        tabLayout.setTabData(arr);
        tabLayout.setOnTabSelectListener(new MyTabSelectListener());

        loadDataCompleted();
    }

    private final class MyPagerAdapter extends SectionsPagerAdapter {

        MyPagerAdapter(List<String> fragmentTitles, FragmentManager fm) {
            super(fragmentTitles, fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MovieListFragment.newInstance(i, "上海", "");
        }
    }

    private final class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tabLayout.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private final class MyTabSelectListener implements OnTabSelectListener {

        @Override
        public void onTabSelect(int position) {
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onTabReselect(int position) {

        }
    }
}