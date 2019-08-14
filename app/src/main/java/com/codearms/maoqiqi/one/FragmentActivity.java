package com.codearms.maoqiqi.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

/**
 * 显示一个标题和一个Fragment
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-13 14:37
 */
public abstract class FragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(getLayoutId());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!(getFragment() != null && savedInstanceState == null)) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getTag());
        Log.e("info", "fragment == null : " + (fragment == null));
        if (fragment == null) {
            fragment = getFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, getTag()).commit();
        }
    }

    protected abstract int getLayoutId();

    protected abstract String getTag();

    protected abstract Fragment getFragment();
}