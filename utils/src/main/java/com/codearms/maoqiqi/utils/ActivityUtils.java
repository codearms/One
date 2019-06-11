package com.codearms.maoqiqi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 11:55
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void startActivity(Context context, Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * Return the activity by view.
     *
     * @param view The view.
     * @return the activity by view.
     */
    public static Activity getActivityByView(@NonNull View view) {
        return getActivityByContext(view.getContext());
    }

    /**
     * Return the activity by context.
     *
     * @param context The context.
     * @return the activity by context.
     */
    public static Activity getActivityByContext(@NonNull Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}