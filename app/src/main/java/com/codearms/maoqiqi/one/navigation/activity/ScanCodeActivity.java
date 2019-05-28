package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.utils.StatusBarUtils;

public class ScanCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_scan_code);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}