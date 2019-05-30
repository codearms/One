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

import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.navigation.fragment.WebViewFragment;
import com.codearms.maoqiqi.one.utils.ActivityUtils;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Toasty;
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
        start(context, 0, url);
    }

    public static void start(@NonNull Context context, int id, @NonNull String url) {
        start(context, DEFAULT_POSITION, id, url);
    }

    public static void start(@NonNull Context context, int position, int id, @NonNull String url) {
        start(context, position, id, "", url);
    }

    public static void start(@NonNull Context context, int position, int id, @Nullable String title, @NonNull String url) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("id", id);
        bundle.putString("title", title);
        bundle.putString("url", url);
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

        position = bundle.getInt("position", DEFAULT_POSITION);
        int id = bundle.getInt("id");
        String title = bundle.getString("title", "");
        String url = bundle.getString("url", "");

        statusBarView.setBackgroundResource(bgResIds[position]);
        toolbar.setBackgroundResource(bgResIds[position]);
        toolbar.setPopupTheme(themeIds[position]);
        if (!title.equals("")) toolbar.setTitle(title);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_more));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        fragment = (WebViewFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = WebViewFragment.newInstance(bgResIds[position], id, title, url);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
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
            case R.id.menu_collect:
                // 收藏列表
                if (App.getInstance().getUserBean() == null) {
                    Toasty.show(this, getString(R.string.please_login));
                } else {
                    ActivityUtils.startActivity(this, CollectActivity.class);
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragment.getWebView().canGoBack()) fragment.getWebView().goBack();
        else super.onBackPressed();
    }
}