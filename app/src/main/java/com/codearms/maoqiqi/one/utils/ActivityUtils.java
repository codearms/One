package com.codearms.maoqiqi.one.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class ActivityUtils {

    public static void startActivity(Context context, Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}