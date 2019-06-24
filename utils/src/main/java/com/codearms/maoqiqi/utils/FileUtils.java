package com.codearms.maoqiqi.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-21 13:59
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static String saveLogToFile(String path, String data) {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA).format(new Date());
            String fileName = "crash-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(Environment.getExternalStorageDirectory(), path);
                if (!dir.exists()) {
                    boolean flag = dir.mkdir();
                    LogUtils.d(TAG, "flag:" + flag);
                }
                FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                fos.write(data.getBytes());
            }
            return fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}