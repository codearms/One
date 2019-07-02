package com.codearms.maoqiqi.one.utils;

import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-02 16:35
 */
public class BookUtils {

    /**
     * 格式化作者
     */
    public static String formatAuthor(List<String> authorList) {
        if (authorList != null && authorList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < authorList.size(); i++) {
                if (i < authorList.size() - 1) {
                    stringBuilder.append(authorList.get(i)).append(" / ");
                } else {
                    stringBuilder.append(authorList.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "佚名";
        }
    }
}