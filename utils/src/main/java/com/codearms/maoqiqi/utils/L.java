package com.codearms.maoqiqi.utils;

import android.util.Log;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 11:55
 */
public final class L {

    public static boolean SHOW_LOG = true;

    public static void verbose(String tag, String msg) {
        if (SHOW_LOG) Log.v(tag, msg);
    }

    public static void debug(String tag, String msg) {
        if (SHOW_LOG) Log.d(tag, msg);
    }

    public static void info(String tag, String msg) {
        if (SHOW_LOG) Log.i(tag, msg);
    }

    public static void warn(String tag, String msg) {
        if (SHOW_LOG) Log.w(tag, msg);
    }

    public static void error(String tag, String msg) {
        if (SHOW_LOG) Log.e(tag, msg);
    }
}