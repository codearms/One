package com.codearms.maoqiqi.one.navigation.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.ActivityUtils;

/**
 * 关于我们
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 15:30
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvVersionName = findViewById(R.id.tv_version_name);

        setSupportActionBar(toolbar);

        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvVersionName.setText(getString(R.string.current_version, versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        findViewById(R.id.tv_project_introduction).setOnClickListener(this);
        findViewById(R.id.tv_check_update).setOnClickListener(this);
        findViewById(R.id.tv_update_description).setOnClickListener(this);
        findViewById(R.id.tv_star).setOnClickListener(this);
        findViewById(R.id.tv_gank).setOnClickListener(this);
        findViewById(R.id.tv_dou_ban).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_project_introduction:
                ActivityUtils.startActivity(this, ProjectIntroductionActivity.class);
                break;
            case R.id.tv_check_update:
                WebViewActivity.start(this, getString(R.string.project_git));
                break;
            case R.id.tv_update_description:
                ActivityUtils.startActivity(this, UpdateDescriptionActivity.class);
                break;
            case R.id.tv_star:
                WebViewActivity.start(this, getString(R.string.project_git));
                break;
            case R.id.tv_gank:
                WebViewActivity.start(this, getString(R.string.gank_api));
                break;
            case R.id.tv_dou_ban:
                WebViewActivity.start(this, getString(R.string.dou_ban_terms));
                break;
        }
    }
}