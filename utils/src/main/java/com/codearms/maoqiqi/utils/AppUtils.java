package com.codearms.maoqiqi.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-21 12:03
 */
public final class AppUtils {

    /**
     * Return the application's version name.
     *
     * @param application Application
     * @return the application's version name
     */
    public static String getAppVersionName(final Application application) {
        try {
            PackageManager pm = application.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @param application Application
     * @return the application's version code
     */
    public static int getAppVersionCode(final Application application) {
        try {
            PackageManager pm = application.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}