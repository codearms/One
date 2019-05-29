package com.codearms.maoqiqi.one.navigation.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.one.utils.Toasty;
import com.codearms.maoqiqi.one.view.ObservableWebView;

public class WebViewFragment extends LazyLoadFragment implements WebViewContract.View {

    private WebViewContract.Presenter presenter;

    private ObservableWebView webView;
    private ProgressBar progressBar;
    private FloatingActionButton fabCollection;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment newInstance(@NonNull String url, int bgResId) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putInt("bgResId", bgResId);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setPresenter(WebViewContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        webView = rootView.findViewById(R.id.web_view);
        progressBar = rootView.findViewById(R.id.progress_bar);
        fabCollection = rootView.findViewById(R.id.fab_collection);

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
        Bundle bundle = getArguments();
        if (bundle == null) return;

        String url = bundle.getString("url", getString(R.string.project_git));
        url = url.replace("http://www.wanandroid.com", "https://www.wanandroid.com");
        url = url.replace("http://www.github.com", "https://www.github.com");
        int bgResId = bundle.getInt("bgResId", R.color.color_navigation);

        webView.loadUrl(url);
        webView.setOnScrollChangeListener((l, t, oldl, oldt) -> {
            if (t - oldt > 0) {
                fabCollection.hide();
            } else {
                fabCollection.show();
            }
        });

        fabCollection.setBackgroundTintList(ContextCompat.getColorStateList(context, bgResId));
        fabCollection.setOnClickListener(v -> presenter.collect(1848));
    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void collectSuccess() {
        Toasty.show(context, "收藏成功");
    }

    @Override
    public void showErrorMessage(String message) {
        Toasty.show(context, message);
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
            if (context instanceof WebViewActivity) {
                Toolbar toolbar = ((WebViewActivity) context).getToolbar();
                if (toolbar != null) toolbar.setTitle(title);
            }
        }
    }
}