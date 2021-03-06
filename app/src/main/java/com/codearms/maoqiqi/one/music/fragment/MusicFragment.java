package com.codearms.maoqiqi.one.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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

import java.util.Arrays;
import java.util.List;

/**
 * 音乐
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-23 14:00
 */
public class MusicFragment extends BaseFragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MusicFragment.
     */
    public static MusicFragment newInstance() {
        return new MusicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_music, container, false);
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

        String[] arr = getResources().getStringArray(R.array.music_titles);
        viewPager.setAdapter(new MyPagerAdapter(Arrays.asList(arr), getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(arr.length - 1);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);

        loadDataCompleted();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getUserVisibleHint()) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
            }
        }
    }

    private final class MyPagerAdapter extends SectionsPagerAdapter {

        MyPagerAdapter(List<String> fragmentTitles, FragmentManager fm) {
            super(fragmentTitles, fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MusicListFragment.newInstance(i);
        }
    }
}