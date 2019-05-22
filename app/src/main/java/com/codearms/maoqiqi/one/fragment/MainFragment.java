package com.codearms.maoqiqi.one.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codearms.maoqiqi.one.R;

public class MainFragment extends Fragment {

    private int[] rbIds = {R.id.rbHome, R.id.rbNews, R.id.rbBook, R.id.rbMusic, R.id.rbMovie};

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    // 选中Fragment对应的位置
    private int position = 0;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager = rootView.findViewById(R.id.view_pager);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position", 0);
        }

        // 设置ViewPager
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(rbIds.length - 1);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        // 设置默认选中首页
        radioGroup.check(rbIds[position]);
        // 设置RadioGroup的监听
        radioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存当前选中位置
        outState.putInt("position", position);
    }

    // 得到对应的Fragment
    private Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return NewsFragment.newInstance();
            case 2:
                return BookFragment.newInstance();
            case 3:
                return MusicFragment.newInstance();
            case 4:
                return MovieFragment.newInstance();
        }
        return null;
    }

    private final class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // FragmentPagerAdapter内部已经做了缓存
        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return rbIds.length;
        }
    }

    private final class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private final class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rbHome:
                    position = 0;
                    break;
                case R.id.rbNews:
                    position = 1;
                    break;
                case R.id.rbBook:
                    position = 2;
                    break;
                case R.id.rbMusic:
                    position = 3;
                    break;
                case R.id.rbMovie:
                    position = 4;
                    break;
            }
            viewPager.setCurrentItem(position);
        }
    }
}