package com.codearms.maoqiqi.utils;

import android.util.Log;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 11:55
 */
public final class LogUtils {

    public static boolean SHOW_LOG = true;

    public static void v(String tag, String msg) {
        if (SHOW_LOG) Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG) Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) Log.e(tag, msg);
    }
}