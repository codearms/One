package com.codearms.maoqiqi.utils;

import android.content.Context;
import android.widget.Toast;

public final class T {

    public static void show(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}