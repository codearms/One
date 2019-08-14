package com.codearms.maoqiqi.one;

import android.app.Application;

import com.codearms.maoqiqi.utils.DensityUtils;
import com.codearms.maoqiqi.utils.LogUtils;
import com.codearms.maoqiqi.utils.PrefUtils;
import com.codearms.maoqiqi.utils.ScreenUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

public class App extends Application {

    private static App INSTANCE = null;

    private boolean isLogin;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        isLogin = false;
        LogUtils.init(true);
        ToastUtils.init(this);
        ScreenUtils.init(this);
        DensityUtils.init(this);
        PrefUtils.init(this);
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setUserName(String userName) {
        PrefUtils.putString("userName", userName);
    }

    public String getUserName() {
        return PrefUtils.getString("userName", "");
    }

    public void setPassword(String password) {
        PrefUtils.putString("password", password);
    }

    public String getPassword() {
        return PrefUtils.getString("password", "");
    }
}