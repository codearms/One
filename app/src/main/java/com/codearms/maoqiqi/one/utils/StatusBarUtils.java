package com.codearms.maoqiqi.one.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) result = context.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    // 设置状态栏颜色
    public static void setStatusColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0及以上,不设置透明状态栏,会有半透明阴影
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置statusBar的背景色
            activity.getWindow().setStatusBarColor(color);
        }
    }

    // 将状态栏清空
    public static void setFullScreen(Activity activity) {
        Window window = activity.getWindow();
        // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN:Activity全屏显示,但是状态栏不会被覆盖掉,而是正常显示,只是Activity顶端布局会被覆盖住
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        // 不设置会有半透明阴影
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置透明状态栏后,会有颜色,再次设置颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}