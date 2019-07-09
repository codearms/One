package com.codearms.maoqiqi.one.gank.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.gank.fragment.GankListFragment;
import com.codearms.maoqiqi.one.gank.fragment.HeaderImageFragment;
import com.codearms.maoqiqi.one.utils.AppBarStateChangeListener;
import com.codearms.maoqiqi.one.utils.SectionsPagerAdapter;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 12:00
 */
public class GankActivity extends BaseActivity implements View.OnClickListener, HeaderImageFragment.HeaderImageCallBack {

    private static final String TAG = "com.codearms.maoqiqi.one.HeaderImageFragment";

    private Animation animation;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView tvSearchGank;
    private ImageView ivCollection;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabBelle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_gank);

        appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        tvSearchGank = findViewById(R.id.tv_search_gank);
        ivCollection = findViewById(R.id.iv_collection);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fabBelle = findViewById(R.id.fab_belle);

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setInterpolator(new LinearInterpolator());
        getHeaderImage();

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    fabBelle.show();
                } else {
                    fabBelle.hide();
                }
            }
        });
        tvSearchGank.setOnClickListener(this);
        ivCollection.setOnClickListener(this);
        fabBelle.setOnClickListener(this);

        String[] arr = getResources().getStringArray(R.array.gank_classify);
        viewPager.setAdapter(new MyPagerAdapter(Arrays.asList(arr), getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_belle:
                getHeaderImage();
                break;
        }
    }

    private void getHeaderImage() {
        fabBelle.setEnabled(false);
        fabBelle.setImageResource(R.drawable.ic_loading);
        fabBelle.startAnimation(animation);

        HeaderImageFragment fragment = (HeaderImageFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = HeaderImageFragment.newInstance();
            fragment.setHeaderImageCallBack(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        } else {
            fragment.getHeaderImage();
        }
    }

    @Override
    public void onHeaderImage(Bitmap resource) {
        fabBelle.clearAnimation();
        fabBelle.setEnabled(true);
        fabBelle.setImageResource(R.drawable.ic_belle);

        int color = Utils.getLightColor(resource);
        collapsingToolbarLayout.setContentScrimColor(color);
        Utils.setTint(fabBelle, color);
    }

    private final class MyPagerAdapter extends SectionsPagerAdapter {

        MyPagerAdapter(List<String> fragmentTitles, FragmentManager fm) {
            super(fragmentTitles, fm);
        }

        @Override
        public Fragment getItem(int i) {
            return GankListFragment.newInstance(i == 0 ? "all" : fragmentTitles.get(i));
        }
    }
}