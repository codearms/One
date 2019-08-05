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

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvVersionName = findViewById(R.id.tv_version_name);
        TextView tvFunctionIntroduction = findViewById(R.id.tv_function_introduction);
        TextView tvCheckUpdate = findViewById(R.id.tv_check_update);
        TextView tvUpdateDescription = findViewById(R.id.tv_update_description);
        TextView tvStar = findViewById(R.id.tv_star);

        setSupportActionBar(toolbar);

        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvVersionName.setText(getString(R.string.current_version, versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        String gankText = "<font color='#191919'>《<u>代码家 · 干货集中营</u>》</font>";
//        tvGank.setText(Html.fromHtml(gankText));
//
//        String douBanText = "<font color='#191919'>《<u>豆瓣开发者服务使用条款</u>》</font>";
//        tvDouBan.setText(Html.fromHtml(douBanText));

        tvFunctionIntroduction.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
        tvUpdateDescription.setOnClickListener(this);
        tvStar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tvProjectIntroduction:
//                ActivityUtils.startActivity(activity, ProjectIntroductionActivity.class);
//                break;
//            case R.id.tvCheckUpdate:
//                WebViewActivity.start(activity, R.color.colorNavigation, "", getString(R.string.project_url));
//                break;
//            case R.id.tvUpdateDescription:
//                ActivityUtils.startActivity(activity, UpdateDescriptionActivity.class);
//                break;
//            case R.id.tvStar:
//                WebViewActivity.start(activity, R.color.colorNavigation, "", getString(R.string.project_url));
//                break;
//            case R.id.tvGank:
//                WebViewActivity.start(activity, R.color.colorNavigation, "", getString(R.string.gank_api));
//                break;
//            case R.id.tvDouBan:
//                WebViewActivity.start(activity, R.color.colorNavigation, "", getString(R.string.dou_ban_terms));
//                break;
//
//        }
    }
}