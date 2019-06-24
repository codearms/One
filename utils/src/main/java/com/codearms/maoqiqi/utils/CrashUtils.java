package com.codearms.maoqiqi.utils;

import android.app.Application;
import android.os.Looper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-21 11:50
 */
public class CrashUtils implements Thread.UncaughtExceptionHandler {

    private static CrashUtils instance;

    private Application application;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler defaultHandler;

    // 保证只有一个CrashHandler实例
    private CrashUtils(Application application) {
        this.application = application;
        // 获取系统默认的UncaughtException处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    // 初始化
    public static void init(Application application) {
        if (instance == null) {
            synchronized (CrashUtils.class) {
                if (instance == null) {
                    instance = new CrashUtils(application);
                }
            }
        }
    }

    // 当UncaughtException发生时会转入该函数来处理
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && defaultHandler != null) {
            defaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息,发送错误报告等操作均在此完成
     *
     * @param e Throwable
     * @return true:处理了该异常信息 否则返回false
     */
    private boolean handleException(Throwable e) {
        // 使用Toast来显示异常信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.show(application, "很抱歉,程序出现异常,即将退出!");
                Looper.loop();
            }
        }).start();

        // 收集设备参数信息
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = DeviceUtils.getDeviceInfo();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        sb.append(writer.toString());

        // 保存错误信息到文件中
        String path = application.getPackageName() + "/crash";
        String fileName = FileUtils.saveLogToFile(path, sb.toString());

        return fileName != null;
    }
}