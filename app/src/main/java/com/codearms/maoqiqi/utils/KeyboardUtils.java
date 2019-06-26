package com.codearms.maoqiqi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-12 14:00
 */
public class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * Return whether soft input is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(@NonNull final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        // 获取View可见区域的bottom
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        return decorView.getHeight() > outRect.bottom + getNavBarHeight();
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId == 0 ? 0 : res.getDimensionPixelSize(resourceId);
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    public static void showSoftInput(@NonNull final View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        hideSoftInput(view);
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(@NonNull final View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}