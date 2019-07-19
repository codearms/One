/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.codearms.maoqiqi.one.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.InputStream;

/**
 * Description：WebViewUtils
 * Created by：CaMnter
 * Time：2016-01-09 17:45
 */
public class WebViewUtils {

    // Inject CSS method: read style.css from assets folder
    // Append stylesheet to document head
    @SuppressLint("SetJavaScriptEnabled")
    public static void injectCSS(Context context, WebView webView, String filename) {
        try {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setDatabaseEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setSaveFormData(false);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.getSettings().setLoadWithOverviewMode(false);
            webView.getSettings().setUseWideViewPort(true);
            InputStream inputStream = context.getAssets().open(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWebViewOptions(WebView webView) {
        Context context = webView.getContext().getApplicationContext();
        //设置编码
        webView.getSettings().setDefaultTextEncodingName("UTF-8");

        //设置缓存
        webView.getSettings().setDomStorageEnabled(true); //开启DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true); //开启database storage API 功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webView.getSettings().setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //设置WebView视图大小与HTML中viewport Tag的关系
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        File cacheFile = context.getCacheDir();
        if (cacheFile != null) {
            webView.getSettings().setAppCachePath(cacheFile.getAbsolutePath());
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                webView.getContext().startActivity(intent);
            }
        });

//        WebSettings webSettings = mWebView.getSettings();
//
//        //支持获取手势焦点，输入用户名、密码或其他
//        mWebView.requestFocusFromTouch();
//
//        webSettings.setJavaScriptEnabled(true);  //支持js
//
//        //设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
//
//        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
//        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
//
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.supportMultipleWindows();  //多窗口
//        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
//        webSettings.setAllowFileAccess(true);  //设置可以访问文件
//        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    //WebViewClient就是帮助WebView处理各种通知、请求事件的。
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

//            onPageLoadFinished(view, url);
        }

        //        shouldOverrideUrlLoading(WebView view, String url)  最常用的，比如上面的。
//        //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
//        //比如获取url，查看url.contains(“add”)，进行添加操作
//
//        shouldOverrideKeyEvent(WebView view, KeyEvent event)
//        //重写此方法才能够处理在浏览器中的按键事件。
//
//        onPageStarted(WebView view, String url, Bitmap favicon)
//        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
//
//        onPageFinished(WebView view, String url)
//        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。
//
//        onLoadResource(WebView view, String url)
//        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
//
//        onReceivedError(WebView view, int errorCode, String description, String failingUrl)
//        // (报告错误信息)
//
//        doUpdateVisitedHistory(WebView view, String url, boolean isReload)
//        //(更新历史记录)
//
//        onFormResubmission(WebView view, Message dontResend, Message resend)
//        //(应用程序重新请求网页数据)
//
//        onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,String realm)
//        //（获取返回信息授权请求）
//
//        onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
//        //重写此方法可以让webview处理https请求。
//
//        onScaleChanged(WebView view, float oldScale, float newScale)
//        // (WebView发生改变时调用)
//
//        onUnhandledKeyEvent(WebView view, KeyEvent event)
//        //（Key事件未被加载时调用）
    }

    //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            if (newProgress == 0) {
//                loadStart();
//            } else if (newProgress > 90) {
//                mProgressBar.setVisibility(View.GONE);
//            } else {
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
        }
//        //获取Web页中的title用来设置自己界面中的title
//        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
//        //因此建议当触发onReceiveError时，不要使用获取到的title
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            MainActivity.this.setTitle(title);
//        }
//
//        @Override
//        public void onReceivedIcon(WebView view, Bitmap icon) {
//            //
//        }
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            //
//            return true;
//        }
//
//        @Override
//        public void onCloseWindow(WebView window) {
//        }
//
//        //处理alert弹出框，html 弹框的一种方式
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
//
//        //处理confirm弹出框
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
//                result) {
//            //
//            return true;
//        }
//
//        //处理prompt弹出框
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
    }
}
