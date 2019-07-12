package com.codearms.maoqiqi.one.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-11 16:04
 */
public class TimeUtils {

    /**
     * 如果在1分钟之内发布的显示"刚刚",如果在1个小时之内发布的显示"XX分钟之前",如果在1天之内发布的显示"XX小时之前"。
     * 如果在今年的1天之外的只显示"月-日",例如"05-03",如果不是今年的显示"年-月-日",例如"2014-03-11"。
     *
     * @param time time
     * @return time
     */
    public static String getTranslateTime(String time) {
        long timeMilliseconds = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);

        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值

        Date date;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) timeMilliseconds = date.getTime();

        long timeDifferent = currentMilliseconds - timeMilliseconds;


        if (timeDifferent < 60 * 1000) {// 一分钟之内
            return "刚刚";
        } else if (timeDifferent < 60 * 60 * 1000) {// 一小时之内
            long minute = timeDifferent / 60000;
            return minute + "分钟之前";
        } else if (timeDifferent < 24 * 60 * 60 * 1000) {// 小于一天
            long hour = timeDifferent / 3600000;
            return hour + "小时之前";
        } else {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
    }
}