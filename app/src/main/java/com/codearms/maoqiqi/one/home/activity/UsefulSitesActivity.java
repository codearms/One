package com.codearms.maoqiqi.one.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.fragment.UsefulSitesFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class UsefulSitesActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.UsefulSitesFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_useful_sites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UsefulSitesFragment fragment = (UsefulSitesFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = UsefulSitesFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }
}