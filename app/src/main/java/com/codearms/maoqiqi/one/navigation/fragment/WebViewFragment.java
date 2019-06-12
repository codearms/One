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

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.home.fragment.ArticlesFragment;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;
import com.codearms.maoqiqi.one.navigation.presenter.WebViewPresenter;
import com.codearms.maoqiqi.one.navigation.presenter.contract.WebViewContract;
import com.codearms.maoqiqi.one.view.ObservableWebView;
import com.codearms.maoqiqi.utils.ToastUtils;

public class WebViewFragment extends BaseFragment<WebViewContract.Presenter> implements WebViewContract.View {

    private WebViewContract.Presenter presenter;

    private ObservableWebView webView;
    private ProgressBar progressBar;
    private FloatingActionButton fabCollection;
    private boolean hide = false;

    private String titleText;
    private String url;
    private String from;
    private ArticleBean articleBean;

    private boolean isCollect;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment newInstance(int bgResId, @NonNull String url) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        bundle.putInt("bgResId", bgResId);
        bundle.putString("url", url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WebViewFragment newInstance(int bgResId, String from, ArticleBean articleBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("bgResId", bgResId);
        bundle.putString("from", from);
        bundle.putParcelable("articleBean", articleBean);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WebViewPresenter(this);
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

        int type = bundle.getInt("type");
        int bgResId = bundle.getInt("bgResId");
        if (type == 1) {
            url = bundle.getString("url", getString(R.string.project_git));
        } else {
            from = bundle.getString("from", "");
            articleBean = bundle.getParcelable("articleBean");
            assert articleBean != null;
            url = articleBean.getLink();
            isCollect = from.equals(ArticlesFragment.FROM_COLLECT) || articleBean.isCollect();
        }

        webView.loadUrl(url);
        webView.setOnScrollChangeListener((l, t, oldl, oldt) -> {
            if (hide || t - oldt > 0) {
                fabCollection.hide();
            } else {
                fabCollection.show();
            }
        });

        fabCollection.setBackgroundTintList(ContextCompat.getColorStateList(context, bgResId));
        fabCollection.setImageResource(isCollect ? R.drawable.ic_collect : R.drawable.ic_un_collect);
        fabCollection.setOnClickListener(v -> {
            if (App.getInstance().getUserBean() == null) {
                ToastUtils.show(context, getString(R.string.please_login));
                return;
            }

            if (isCollect) {
                // 收藏页面(自定义收藏内容取消收藏需要调用unCollect(int id, int originId))
                if (from == null || from.equals(ArticlesFragment.FROM_COLLECT)) {
                    presenter.unCollect(articleBean.getId(), articleBean.getOriginId());
                } else {
                    presenter.unCollect(articleBean.getId());
                }
            } else {
                // 自定义收藏内容
                if (from == null) {
                    presenter.collect(titleText, App.getInstance().getUserBean().getUserName(), url);
                } else {
                    presenter.collect(articleBean.getId());
                }
            }
        });
    }

    public WebView getWebView() {
        return webView;
    }

    @Override
    public void collectSuccess(ArticleBean articleBean) {
        if (articleBean != null) this.articleBean = articleBean;
        isCollect = true;
        fabCollection.setImageResource(R.drawable.ic_collect);
        showErrorMsg(getString(R.string.success_to_collect));
    }

    @Override
    public void unCollectSuccess() {
        isCollect = false;
        if (from.equals(ArticlesFragment.FROM_COLLECT)) {
            // 收藏页面的取消之后不能再次收藏
            hide = true;
            fabCollection.hide();
        } else {
            fabCollection.setImageResource(R.drawable.ic_un_collect);
        }
        showErrorMsg(getString(R.string.success_to_un_collect));
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
            if (titleText != null && !titleText.equals("")) return;

            titleText = title;
            if (context instanceof WebViewActivity) {
                Toolbar toolbar = ((WebViewActivity) context).getToolbar();
                if (toolbar != null) toolbar.setTitle(title);
            }
        }
    }
}