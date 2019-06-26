package com.codearms.maoqiqi.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.Random;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-11 14:52
 */
public class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 获取随机rgb颜色值
    @ColorInt
    public static int randomDarkColor() {
        Random random = new Random();
        // 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        // 使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

    private static int[] randomColor() {
        Random random = new Random();
        int red = 255 - random.nextInt(150);
        int green = 255 - random.nextInt(150);
        int blue = 255 - random.nextInt(150);
        return new int[]{Color.rgb(red, green, blue), Color.argb(150, red, green, blue)};
    }

    // 获取随机背景
    public static ColorStateList createColorStateList() {
        int[] colors = randomColor();
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }
}