package com.codearms.maoqiqi.one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.book.fragment.BookFragment;
import com.codearms.maoqiqi.one.home.fragment.HomeFragment;
import com.codearms.maoqiqi.one.movie.fragment.MovieFragment;
import com.codearms.maoqiqi.one.music.fragment.MusicFragment;
import com.codearms.maoqiqi.one.navigation.activity.AboutActivity;
import com.codearms.maoqiqi.one.navigation.activity.CollectActivity;
import com.codearms.maoqiqi.one.navigation.activity.DonateActivity;
import com.codearms.maoqiqi.one.navigation.activity.LoginActivity;
import com.codearms.maoqiqi.one.navigation.activity.ProblemFeedbackActivity;
import com.codearms.maoqiqi.one.navigation.activity.ProjectIntroductionActivity;
import com.codearms.maoqiqi.one.navigation.activity.ScanCodeActivity;
import com.codearms.maoqiqi.one.navigation.activity.SearchActivity;
import com.codearms.maoqiqi.one.navigation.activity.SettingActivity;
import com.codearms.maoqiqi.one.navigation.activity.UpdateDescriptionActivity;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.news.fragment.NewsFragment;
import com.codearms.maoqiqi.one.utils.FragmentCheckedChangeListener;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Utils;
import com.codearms.maoqiqi.utils.ActivityUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

/**
 * 主页面
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 10:10
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final int[] buttonIds = {R.id.rb_home, R.id.rb_news, R.id.rb_book, R.id.rb_music, R.id.rb_movie};

    private DrawerLayout drawerLayout;
    private TextView tvUserName;
    private MenuItem logoutMenu;

    private MyOnCheckedChangeListener checkedChangeListener;

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

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
        TextView tvWeather = findViewById(R.id.tv_weather);

        View navigationHeader = navigationView.getHeaderView(0);
        // 头像
        navigationHeader.findViewById(R.id.iv_avatar).setOnClickListener(this);
        // 用户名
        tvUserName = navigationHeader.findViewById(R.id.tv_user_name);
        // 扫码
        navigationHeader.findViewById(R.id.iv_scan_code).setOnClickListener(this);

        // 登出
        logoutMenu = navigationView.getMenu().findItem(R.id.nav_logout);
        // 设置菜单点击事件
        navigationView.setNavigationItemSelectedListener(this);

        setUserInfo();
        Utils.setWeather(tvWeather, "30°", "徐家汇");

        tvUserName.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvMode.setOnClickListener(this);
        tvWeather.setOnClickListener(this);

        // 默认加载第一项
        int position = 0;
        checkedChangeListener = new MyOnCheckedChangeListener(getSupportFragmentManager(), buttonIds, R.id.fl_content);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position", 0);
            checkedChangeListener.findFragmentByTag();
        }

        // 设置RadioGroup的监听
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
        // 设置默认选项
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 从新进入都需要更新信息
        setUserInfo();
        Fragment fragment = checkedChangeListener.getFragments()[0];
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).updateData();
        }
    }

    // 更新用户信息
    private void setUserInfo() {
        if (App.getInstance().isLogin()) {
            tvUserName.setText(App.getInstance().getUserName());
            logoutMenu.setVisible(true);
        } else {
            tvUserName.setText(R.string.login);
            logoutMenu.setVisible(false);
        }
    }

    // 将Toolbar 与 DrawerLayout 关联
    public void associateDrawerLayout(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
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
            case R.id.iv_avatar:
                WebViewActivity.start(this, getString(R.string.project_git));
                break;
            case R.id.iv_scan_code:
                ActivityUtils.startActivity(this, ScanCodeActivity.class);
                break;
            case R.id.tv_user_name:
                if (!App.getInstance().isLogin()) {
                    ActivityUtils.startActivity(this, LoginActivity.class);
                } else {
                    ActivityUtils.startActivity(this, CollectActivity.class);
                }
                break;
            case R.id.tv_setting:
                ActivityUtils.startActivity(this, SettingActivity.class);
                break;
            case R.id.tv_mode:
                ToastUtils.show("开发中");
                break;
            case R.id.tv_weather:
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
            case R.id.nav_logout:
                // App.getInstance().setUserName("");
                // App.getInstance().setPassword("");
                // App.getInstance().setIsLogin(false);
                ToastUtils.show("开发中");
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
            SearchActivity.start(this, checkedChangeListener.getPosition());
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

    private final class MyOnCheckedChangeListener extends FragmentCheckedChangeListener {

        MyOnCheckedChangeListener(FragmentManager fm, int[] buttonIds, int resId) {
            super(fm, buttonIds, resId);
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