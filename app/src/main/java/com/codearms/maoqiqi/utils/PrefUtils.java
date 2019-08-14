package com.codearms.maoqiqi.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Utils about SharedPreferences.
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-14 15:34
 * <p>
 * apply与commit异同:
 * 1.apply没有返回值而commit返回boolean表明修改是否提交成功.
 * 2.apply是将修改数据原子提交到内存,而后异步真正提交到硬件磁盘,而commit是同步的提交到硬件磁盘。
 * 因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
 * 而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。
 * 3.apply方法不会提示任何失败的提示。
 * 由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，如果对提交的结果不关心的话，建议使用apply。
 * 当然需要确保提交成功且有后续操作的话，还是需要用commit的。
 */
public final class PrefUtils {

    private static final String ERR_MSG = "You need first to call PrefUtils.init(application).";

    private static SharedPreferences preferences;

    private PrefUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void init(Application application) {
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public static String getString(final String key, final String defaultValue) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        return preferences.getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        preferences.edit().putString(key, value).apply();
    }

    public static int getInt(final String key, int defaultValue) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        return preferences.getInt(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        preferences.edit().putInt(key, value).apply();
    }

    public static long getLong(final String key, long defaultValue) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        return preferences.getLong(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        preferences.edit().putLong(key, value).apply();
    }

    public static boolean getBoolean(String key, final boolean defaultValue) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        return preferences.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        preferences.edit().putBoolean(key, value).apply();
    }

    public static void remove(String key) {
        if (preferences == null) throw new RuntimeException(ERR_MSG);
        preferences.edit().remove(key).apply();
    }
}