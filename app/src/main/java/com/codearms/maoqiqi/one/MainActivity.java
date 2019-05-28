package com.codearms.maoqiqi.one;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codearms.maoqiqi.one.book.fragment.BookFragment;
import com.codearms.maoqiqi.one.home.fragment.HomeFragment;
import com.codearms.maoqiqi.one.home.utils.ActivityUtils;
import com.codearms.maoqiqi.one.home.utils.FragmentCheckedChangeListener;
import com.codearms.maoqiqi.one.home.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.home.utils.Toasty;
import com.codearms.maoqiqi.one.movie.fragment.MovieFragment;
import com.codearms.maoqiqi.one.music.fragment.MusicFragment;
import com.codearms.maoqiqi.one.navigation.activity.AboutActivity;
import com.codearms.maoqiqi.one.navigation.activity.DonateActivity;
import com.codearms.maoqiqi.one.navigation.activity.LoginActivity;
import com.codearms.maoqiqi.one.navigation.activity.ProblemFeedbackActivity;
import com.codearms.maoqiqi.one.navigation.activity.ProjectIntroductionActivity;
import com.codearms.maoqiqi.one.navigation.activity.ScanCodeActivity;
import com.codearms.maoqiqi.one.navigation.activity.UpdateDescriptionActivity;
import com.codearms.maoqiqi.one.news.fragment.NewsFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private final int[] buttonIds = {R.id.rb_home, R.id.rb_news, R.id.rb_book, R.id.rb_music, R.id.rb_movie};

    private DrawerLayout drawerLayout;

    private MyOnCheckedChangeListener checkedChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        TextView tvSetting = findViewById(R.id.tv_setting);
        TextView tvMode = findViewById(R.id.tv_mode);

        View navigationHeader = navigationView.getHeaderView(0);
        navigationHeader.findViewById(R.id.iv_scan_code).setOnClickListener(this);
        navigationHeader.findViewById(R.id.iv_project).setOnClickListener(this);
        navigationHeader.findViewById(R.id.tv_name).setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        tvSetting.setOnClickListener(this);
        tvMode.setOnClickListener(this);

        // 默认加载第一项
        int position = 0;
        checkedChangeListener = new MyOnCheckedChangeListener(buttonIds, R.id.fl_content);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position", 0);
            checkedChangeListener.findFragmentByTag();
        }

        // 设置RadioGroup的监听
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
        // 设置默认选项
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    // 将Toolbar 与 DrawerLayout 关联
    public void associateDrawerLayout(Toolbar toolbar) {
        // ActionBar三个小点图标设置
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_more));

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存当前选中索引
        outState.putInt("position", checkedChangeListener.getPosition());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan_code:
                ActivityUtils.startActivity(this, ScanCodeActivity.class);
                break;
            case R.id.iv_project:
                ActivityUtils.startActivity(this, LoginActivity.class);
                break;
            case R.id.tv_name:
                WebViewActivity.start(this, getString(R.string.project_git));
                break;
            case R.id.tv_setting:
//                ActivityUtils.startActivity(this, SettingActivity.class);
                break;
            case R.id.tv_mode:
                Toasty.show(this, "开发中");
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_project_introduction:
                ActivityUtils.startActivity(this, ProjectIntroductionActivity.class);
                return true;
            case R.id.nav_update_description:
                ActivityUtils.startActivity(this, UpdateDescriptionActivity.class);
                return true;
            case R.id.nav_scan_code:
                ActivityUtils.startActivity(this, ScanCodeActivity.class);
                return true;
            case R.id.nav_problem_feedback:
                ActivityUtils.startActivity(this, ProblemFeedbackActivity.class);
                return true;
            case R.id.nav_about:
                ActivityUtils.startActivity(this, AboutActivity.class);
                return true;
            case R.id.nav_donate:
                ActivityUtils.startActivity(this, DonateActivity.class);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_search) {
            Toasty.show(this, "search");
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    private final class MyOnCheckedChangeListener extends FragmentCheckedChangeListener {

        MyOnCheckedChangeListener(int[] buttonIds, int resId) {
            super(buttonIds, resId);
        }

        @Override
        protected FragmentManager getSupportFragmentManager() {
            return MainActivity.this.getSupportFragmentManager();
        }

        @Override
        protected Fragment newFragment(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return NewsFragment.newInstance();
                case 2:
                    return BookFragment.newInstance();
                case 3:
                    return MusicFragment.newInstance();
                case 4:
                    return MovieFragment.newInstance();
            }
            return null;
        }
    }
}