package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 11:11
 */
public class SettingActivity extends BaseActivity {


    RelativeLayout rlSystemAnimation;
    SwitchCompat scSystemAnimation;

    RelativeLayout rlLauncherPage;
    TextView tvLauncherPageDesc;
    SwitchCompat scLauncherPage;

    RelativeLayout rlLauncherPageRandom;
    TextView tvLauncherPageRandomTitle;
    TextView tvLauncherPageRandomDesc;
    SwitchCompat scLauncherPageRandom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);

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
            tvLauncherPageDesc.setText("没有妹子太寂寞");
            rlLauncherPageRandom.setClickable(true);
            tvLauncherPageRandomTitle.setTextColor(getResources().getColor(R.color.color_content_main));
            tvLauncherPageRandomDesc.setTextColor(getResources().getColor(R.color.color_content_general));
        } else {
            tvLauncherPageDesc.setText("基佬怎么会需要妹子");
            rlLauncherPageRandom.setClickable(false);
            tvLauncherPageRandomTitle.setTextColor(getResources().getColor(R.color.color_content_secondary));
            tvLauncherPageRandomDesc.setTextColor(getResources().getColor(R.color.color_content_secondary));
        }
    }

    private void setLauncherPageRandomDesc() {
        if (scLauncherPageRandom.isChecked()) {
            tvLauncherPageRandomDesc.setText("偶尔来个惊喜就行");
        } else {
            tvLauncherPageRandomDesc.setText("我每次都要幸临，没毛病");
        }
    }
}