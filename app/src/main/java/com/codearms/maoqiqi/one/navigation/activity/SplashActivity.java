package com.codearms.maoqiqi.one.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.FragmentActivity;
import com.codearms.maoqiqi.one.MainActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.navigation.fragment.LoginFragment;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 闪屏页面
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-05 15:58
 */
public class SplashActivity extends FragmentActivity implements LoginFragment.LoginCallBack {

    private static final String TAG = "com.codearms.maoqiqi.one.LoginFragment";

    private TextView tvJump;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final int COUNT = 5;
    private volatile boolean isFirstStartMain = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected Fragment getFragment() {
        return LoginFragment.newInstance(true).setCallBack(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);

        ImageView ivSplash = findViewById(R.id.iv_splash);
        tvJump = findViewById(R.id.tv_jump);

        ivSplash.post(() -> {
            // 随机加载图片
            int i = new Random().nextInt(Constants.URLS.length);
            Glide.with(SplashActivity.this).load(Constants.URLS[i])
                    .placeholder(R.drawable.ic_splash_placeholder).into(ivSplash);
        });
        loginStatus(true);
    }

    @Override
    public void loginStatus(boolean status) {
        // 调用登录接口之后显示跳过按钮
        showView();

        // 1s之后显示"跳过"按钮
//        compositeDisposable.add(Observable.timer(1000, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> showView()));
    }

    // 显示跳过按钮
    private void showView() {
        tvJump.setVisibility(View.VISIBLE);
        tvJump.setText(getString(R.string.jump_num, COUNT));
        tvJump.setOnClickListener(v -> startMainActivity());
        // 每隔1s执行一次任务,执行5次
        compositeDisposable.add(Observable.interval(1000, TimeUnit.MILLISECONDS).take(COUNT)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
                    tvJump.setText(getString(R.string.jump_num, COUNT - 1 - aLong));
                    if (COUNT - 1 - aLong == 0) {
                        tvJump.setVisibility(View.GONE);
                        startMainActivity();
                    }
                }));
    }

    // 跳转到主页面,并且把当前页面关闭掉
    private void startMainActivity() {
        if (!isFirstStartMain) {
            isFirstStartMain = true;
            startActivity(new Intent(this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // 禁止回退
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}