package com.codearms.maoqiqi.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.fragment.BookFragment;
import com.codearms.maoqiqi.one.fragment.HomeFragment;
import com.codearms.maoqiqi.one.fragment.MovieFragment;
import com.codearms.maoqiqi.one.fragment.MusicFragment;
import com.codearms.maoqiqi.one.fragment.NewsFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class MainActivity extends BaseActivity {

    private final int[] buttonIds = {R.id.rbHome, R.id.rbNews, R.id.rbBook, R.id.rbMusic, R.id.rbMovie};
    private static final String[] TAGS = {"TAG_HOME", "TAG_NEWS", "TAG_BOOK", "TAG_MUSIC", "TAG_MOVIE"};
    private final LazyLoadFragment[] fragments = new LazyLoadFragment[TAGS.length];
    private final int[] bgIds = {R.color.color_home, R.color.color_news, R.color.color_book,
            R.color.color_music, R.color.color_movie};
    private final int[] titleIds = {R.string.home, R.string.news, R.string.book, R.string.music, R.string.movie};
    private final int[] popupThemes = {R.style.home_popup_theme, R.style.news_popup_theme,
            R.style.book_popup_theme, R.style.music_popup_theme, R.style.movie_popup_theme};

    private DrawerLayout drawerLayout;
    private MyOnCheckedChangeListener checkedChangeListener;
//    private StatusBarView statusBarView;
//    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setFullscreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
//        statusBarView = findViewById(R.id.status_bar);
//        toolbar = findViewById(R.id.toolbar);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // 默认加载第一项
        int position = 0;
        checkedChangeListener = new MyOnCheckedChangeListener(buttonIds);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position", 0);
            checkedChangeListener.findFragmentByTag();
        }

        // 设置RadioGroup的监听
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
        // 设置默认选项
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);

//        // 定义drawerArrowDrawable
//        DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(this);
//        drawerArrowDrawable.setColor(ContextCompat.getColor(this, R.color.color_text_title));
//
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
//                R.string.drawer_open, R.string.drawer_close);
//        // 设置drawerArrowDrawable
//        toggle.setDrawerArrowDrawable(drawerArrowDrawable);
//        toggle.syncState();
//        drawerLayout.addDrawerListener(toggle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存当前选中索引
        outState.putInt("position", checkedChangeListener.getPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_search) {
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
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

        MyOnCheckedChangeListener(int[] buttonIds) {
            super(buttonIds);
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