package com.codearms.maoqiqi.one;

import android.app.Application;

import com.codearms.maoqiqi.one.home.data.bean.UserBean;

public class App extends Application {

    private static App INSTANCE = null;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    private UserBean userBean;

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