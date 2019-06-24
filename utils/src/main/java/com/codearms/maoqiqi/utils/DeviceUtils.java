package com.codearms.maoqiqi.utils;

import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-21 12:01
 */
public final class DeviceUtils {

    private static final String TAG = DeviceUtils.class.getSimpleName();

    // 收集设备参数信息
    public static Map<String, String> getDeviceInfo() {
        Map<String, String> map = new HashMap<>();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(null).toString());
                LogUtils.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(TAG, e.getMessage());
            }
        }
        return map;
    }
}