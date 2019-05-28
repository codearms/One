package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.codearms.maoqiqi.one.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.navigation.fragment.RegisterFragment;
import com.codearms.maoqiqi.one.navigation.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.RegisterFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RegisterFragment fragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = RegisterFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }

        new RegisterPresenter(fragment);
    }
}