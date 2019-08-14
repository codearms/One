package com.codearms.maoqiqi.one.navigation.activity;

import android.support.v4.app.Fragment;

import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.navigation.fragment.RegisterFragment;

/**
 * 注册
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 15:15
 */
public class RegisterActivity extends FragmentActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.RegisterFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return RegisterFragment.newInstance();
    }
}