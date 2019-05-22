package com.codearms.maoqiqi.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;

import com.codearms.maoqiqi.one.fragment.MainFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setFullscreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 定义drawerArrowDrawable
        DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(ContextCompat.getColor(this, R.color.color_text_title));

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        // 设置drawerArrowDrawable
        toggle.setDrawerArrowDrawable(drawerArrowDrawable);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        if (getSupportActionBar() != null) getSupportActionBar().setElevation(0);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, MainFragment.newInstance()).commit();
    }
}