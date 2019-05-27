package com.codearms.maoqiqi.one.home.utils;

import android.content.Context;
import android.widget.Toast;

public class Toasty {

    public static void show(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}