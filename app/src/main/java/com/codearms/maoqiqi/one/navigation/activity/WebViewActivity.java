package com.codearms.maoqiqi.one.navigation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.navigation.fragment.WebViewFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.view.StatusBarView;
import com.codearms.maoqiqi.utils.ActivityUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.lang.reflect.Method;

//import com.codearms.maoqiqi.one.utils.DeviceUtils;

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
        start(context, DEFAULT_POSITION, url);
    }

    public static void start(@NonNull Context context, int position, @NonNull String url) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("position", position);
        bundle.putString("url", url);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(@NonNull Context context, String from, @NonNull ArticleBean articleBean) {
        start(context, DEFAULT_POSITION, from, articleBean);
    }

    public static void start(@NonNull Context context, int position, String from, @NonNull ArticleBean articleBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("position", position);
        bundle.putString("from", from);
        bundle.putParcelable("articleBean", articleBean);
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

        int type = bundle.getInt("type");
        position = bundle.getInt("position", DEFAULT_POSITION);

        statusBarView.setBackgroundResource(bgResIds[position]);
        toolbar.setBackgroundResource(bgResIds[position]);
        toolbar.setPopupTheme(themeIds[position]);
        // if (!title.equals("")) toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        fragment = (WebViewFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            if (type == 1) {
                String url = bundle.getString("url", "");
                fragment = WebViewFragment.newInstance(bgResIds[position], url);
            } else {
                String from = bundle.getString("from", "");
                ArticleBean articleBean = bundle.getParcelable("articleBean");
                fragment = WebViewFragment.newInstance(bgResIds[position], from, articleBean);
            }
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
            case R.id.menu_web_refresh:
                // 刷新
                if (fragment != null && fragment.getWebView() != null)
                    fragment.getWebView().reload();
                break;
            case R.id.menu_web_share:
                // 分享
                return true;
            case R.id.menu_copy_url:
                // 复制链接
//                DeviceUtils.copy2Clipboard(this, fragment.getWebView().getUrl());
                ToastUtils.show(getString(R.string.copy_success));
                return true;
            case R.id.menu_browser_open:
                // 浏览器打开
                return true;
            case R.id.menu_collect:
                // 收藏列表
                if (App.getInstance().getUserBean() == null) {
                    ToastUtils.show(getString(R.string.please_login));
                } else {
                    ActivityUtils.startActivity(this, CollectActivity.class);
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment.getWebView().canGoBack()) fragment.getWebView().goBack();
        else super.onBackPressed();
    }
}