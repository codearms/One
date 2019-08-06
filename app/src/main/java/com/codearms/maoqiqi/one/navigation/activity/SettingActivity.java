package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

/**
 * 设置
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 16:15
 */
public class SettingActivity extends BaseActivity {

    private TextView tvLauncherPageDesc;
    private SwitchCompat scLauncherPage;

    private ConstraintLayout rlLauncherPageRandom;
    private TextView tvLauncherPageRandomTitle;
    private TextView tvLauncherPageRandomDesc;
    private SwitchCompat scLauncherPageRandom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ConstraintLayout rlSystemAnimation = findViewById(R.id.rl_system_animation);
        SwitchCompat scSystemAnimation = findViewById(R.id.sc_system_animation);
        ConstraintLayout rlLauncherPage = findViewById(R.id.rl_launcher_page);
        tvLauncherPageDesc = findViewById(R.id.tv_launcher_page_desc);
        scLauncherPage = findViewById(R.id.sc_launcher_page);
        rlLauncherPageRandom = findViewById(R.id.rl_launcher_page_random);
        tvLauncherPageRandomTitle = findViewById(R.id.tv_launcher_page_random_title);
        tvLauncherPageRandomDesc = findViewById(R.id.tv_launcher_page_random_desc);
        scLauncherPageRandom = findViewById(R.id.sc_launcher_page_random);

        setSupportActionBar(toolbar);

        rlSystemAnimation.setOnClickListener(v -> scSystemAnimation.setChecked(!scSystemAnimation.isChecked()));
        rlLauncherPage.setOnClickListener(v -> {
            scLauncherPage.setChecked(!scLauncherPage.isChecked());
            setLauncherPageRandomEnable();
        });
        rlLauncherPageRandom.setOnClickListener(v -> {
            scLauncherPageRandom.setChecked(!scLauncherPageRandom.isChecked());
            setLauncherPageRandomDesc();
        });

        scLauncherPage.setChecked(false);
        setLauncherPageRandomEnable();
        scLauncherPageRandom.setChecked(false);
        setLauncherPageRandomDesc();
    }

    private void setLauncherPageRandomEnable() {
        if (scLauncherPage.isChecked()) {
            tvLauncherPageDesc.setText(R.string.launcher_page_desc_2);
            rlLauncherPageRandom.setClickable(true);
            tvLauncherPageRandomTitle.setTextColor(getResources().getColor(R.color.color_content_main));
            tvLauncherPageRandomDesc.setTextColor(getResources().getColor(R.color.color_content_general));
        } else {
            tvLauncherPageDesc.setText(R.string.launcher_page_desc_1);
            rlLauncherPageRandom.setClickable(false);
            tvLauncherPageRandomTitle.setTextColor(getResources().getColor(R.color.color_content_secondary));
            tvLauncherPageRandomDesc.setTextColor(getResources().getColor(R.color.color_content_secondary));
        }
    }

    private void setLauncherPageRandomDesc() {
        if (scLauncherPageRandom.isChecked()) {
            tvLauncherPageRandomDesc.setText(R.string.launcher_page_random_desc_2);
        } else {
            tvLauncherPageRandomDesc.setText(R.string.launcher_page_random_desc_1);
        }
    }
}