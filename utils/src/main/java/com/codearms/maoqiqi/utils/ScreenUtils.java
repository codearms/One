package com.codearms.maoqiqi.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Helper class for screen
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 11:55
 */
public final class ScreenUtils {

    private static Application application;

    private ScreenUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void init(Application application) {
        ScreenUtils.application = application;
    }

    @Deprecated
    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    @Deprecated
    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    // Getting screen width
    public static int getScreenWidth() {
        if (application == null)
            throw new RuntimeException("Before getting screen width you need to call ScreenUtils.init(application)");
        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    // Getting screen height
    public static int getScreenHeight() {
        if (application == null)
            throw new RuntimeException("Before getting screen height you need to call ScreenUtils.init(application)");
        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    // Getting status bar height
    public static int getStatusBarHeight() {
        if (application == null)
            throw new RuntimeException("Before getting status bar height you need to call ScreenUtils.init(application)");
        int resourceId = application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return application.getResources().getDimensionPixelSize(resourceId);
    }
}