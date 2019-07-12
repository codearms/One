package com.codearms.maoqiqi.one.utils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-11 16:17
 */
public class GankUtils {

    public static String getWho(String who) {
        if (who == null || who.equals("")) return "佚名";
        return who;
    }
}