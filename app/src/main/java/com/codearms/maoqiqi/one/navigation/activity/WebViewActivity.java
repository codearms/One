package com.codearms.maoqiqi.one.navigation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.navigation.fragment.WebViewFragment;
import com.codearms.maoqiqi.one.navigation.presenter.WebViewPresenter;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.view.StatusBarView;

import java.lang.reflect.Method;

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.WebViewFragment";
    private static final int DEFAULT_POSITION = 5;

    private final int[] bgResIds = {R.color.color_home, R.color.color_news, R.color.color_book,
            R.color.color_music, R.color.color_movie, R.color.color_navigation};
    private final int[] themeIds = {R.style.home_popup_theme, R.style.news_popup_theme,
            R.style.book_popup_theme, R.style.music_popup_theme,
            R.style.movie_popup_theme, R.style.navigation_popup_theme};

    private Toolbar toolbar;

    private int position;

    private WebViewFragment fragment;

    public static void start(@NonNull Context context, @NonNull String url) {
        start(context, url, DEFAULT_POSITION);
    }

    public static void start(@NonNull Context context, @NonNull String url, int position) {
        start(context, url, "", position);
    }

    public static void start(@NonNull Context context, @NonNull String url, @Nullable String title) {
        start(context, url, title, DEFAULT_POSITION);
    }

    public static void start(@NonNull Context context, @NonNull String url, @Nullable String title, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putInt("position", position);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_web_view);

        StatusBarView statusBarView = findViewById(R.id.status_bar);
        toolbar = findViewById(R.id.toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String title = bundle.getString("title", "");
        String url = bundle.getString("url", "");
        position = bundle.getInt("position", DEFAULT_POSITION);

        statusBarView.setBackgroundResource(bgResIds[position]);
        toolbar.setBackgroundResource(bgResIds[position]);
        toolbar.setPopupTheme(themeIds[position]);
        if (!title.equals("")) toolbar.setTitle(title);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_more));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        fragment = (WebViewFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = WebViewFragment.newInstance(url, bgResIds[position]);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }

        new WebViewPresenter(fragment);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    @SuppressLint("PrivateApi")
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (int i = 0; i < menu.size(); i++) {
                    Drawable drawable = menu.getItem(i).getIcon();
                    if (drawable != null)
                        drawable.setTint(getResources().getColor(bgResIds[position]));
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_web_share:
                // 分享
                return true;
            case R.id.menu_copy_url:
                // 复制链接
                return true;
            case R.id.menu_browser_open:
                // 浏览器打开
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragment.getWebView().canGoBack()) fragment.getWebView().goBack();
        else super.onBackPressed();
    }
}