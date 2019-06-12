package com.codearms.maoqiqi.utils;

import android.graphics.Color;

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
    public static int randomDarkColor() {
        Random random = new Random();
        // 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        // 使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }
}