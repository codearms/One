package com.codearms.maoqiqi.utils;

import android.app.Application;
import android.util.TypedValue;

/**
 * Helper class for Density
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 11:55
 */
public final class DensityUtils {

    private static Application application;

    private DensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void init(Application application) {
        DensityUtils.application = application;
    }

    public static int dp2px(float dpVal) {
        if (application == null)
            throw new RuntimeException("Before dp to px you need to call DensityUtils.init(application)");
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, application.getResources().getDisplayMetrics());
    }

    public static int sp2px(float spVal) {
        if (application == null)
            throw new RuntimeException("Before sp to px you need to call DensityUtils.init(application)");
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, application.getResources().getDisplayMetrics());
    }

    public static float px2dp(float pxVal) {
        if (application == null)
            throw new RuntimeException("Before px to dp you need to call DensityUtils.init(application)");
        return (pxVal / application.getResources().getDisplayMetrics().density);
    }

    public static float px2sp(float pxVal) {
        if (application == null)
            throw new RuntimeException("Before px to sp you need to call DensityUtils.init(application)");
        return (pxVal / application.getResources().getDisplayMetrics().scaledDensity);
    }
}