package com.codearms.maoqiqi.one.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Toasty;
import com.codearms.maoqiqi.one.view.ObservableWebView;
import com.codearms.maoqiqi.one.view.StatusBarView;

import java.lang.reflect.Method;

public class WebViewActivity extends BaseActivity {

    private final int[] bgResIds = {R.color.color_home, R.color.color_news, R.color.color_book,
            R.color.color_music, R.color.color_movie, R.color.color_navigation};
    private final int[] themeIds = {R.style.home_popup_theme, R.style.news_popup_theme,
            R.style.book_popup_theme, R.style.music_popup_theme,
            R.style.movie_popup_theme, R.style.navigation_popup_theme};
    private static final int DEFAULT_POSITION = 5;

    private StatusBarView statusBarView;
    private Toolbar toolbar;
    private ObservableWebView webView;
    private ProgressBar progressBar;
    FloatingActionButton fabCollection;

    private int position;

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

        statusBarView = findViewById(R.id.status_bar);
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        fabCollection = findViewById(R.id.fab_collection);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_more));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        progressBar.setVisibility(View.VISIBLE);
        setWebView();
        setData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        WebSettings setting = webView.getSettings();
        // 告诉WebView启用JavaScript执行.默认的是false.
        setting.setJavaScriptEnabled(true);
        //  页面加载好以后,再放开图片
        setting.setBlockNetworkImage(false);
        // 网页内容的宽度是否可大于WebView控件的宽度
        setting.setLoadWithOverviewMode(false);
        // 使用localStorage则必须打开
        setting.setDomStorageEnabled(true);
        // 排版适应屏幕
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口.
        setting.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // webView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启.
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置字体默认缩放大小(改变网页字体大小,setTextSize api14 被弃用)
        setting.setTextZoom(100);
        // 设置此属性,可任意比例缩放.
        setting.setUseWideViewPort(true);

        webView.setInitialScale(1);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String title = bundle.getString("title", "");
        String url = bundle.getString("url", "");
        url = url.replace("http://www.wanandroid.com", "https://www.wanandroid.com");
        url = url.replace("http://www.github.com", "https://www.github.com");
        position = bundle.getInt("position", DEFAULT_POSITION);

        statusBarView.setBackgroundResource(bgResIds[position]);
        toolbar.setBackgroundResource(bgResIds[position]);
        toolbar.setPopupTheme(themeIds[position]);
        if (!title.equals("")) toolbar.setTitle(title);
        webView.loadUrl(url);
        webView.setOnScrollChangeListener((l, t, oldl, oldt) -> {
            if (t - oldt > 0) {
                fabCollection.hide();
            } else {
                fabCollection.show();
            }
        });

        fabCollection.setBackgroundTintList(ContextCompat.getColorStateList(this, bgResIds[position]));
        fabCollection.setOnClickListener(v -> Toasty.show(WebViewActivity.this, "Collection"));
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
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    private final class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private final class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (toolbar != null) {
                toolbar.setTitle(title);
            }
        }
    }
}