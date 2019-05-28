package com.codearms.maoqiqi.one;

import android.app.Application;

import com.codearms.maoqiqi.one.data.bean.UserBean;

public class App extends Application {

    private static App INSTANCE = null;

    private UserBean userBean;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}