package com.codearms.maoqiqi.one.home.activity;

import android.support.v4.app.Fragment;

import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.UsefulSitesFragment;

/**
 * 常用网站
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-09 11:15
 */
public class UsefulSitesActivity extends FragmentActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.UsefulSitesFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_useful_sites;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return UsefulSitesFragment.newInstance();
    }
}