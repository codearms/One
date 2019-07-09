package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class ScanCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_scan_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_code, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                return true;
            case R.id.menu_star:
                WebViewActivity.start(this, getString(R.string.project_git));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}