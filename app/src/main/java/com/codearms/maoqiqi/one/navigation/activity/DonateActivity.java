package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class DonateActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_donate);

        Toolbar toolbar = findViewById(R.id.toolbar);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        ImageView ivPay = findViewById(R.id.iv_pay);

        setSupportActionBar(toolbar);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_alipay) {
                ivPay.setImageResource(R.drawable.ic_alipay);
            } else {
                ivPay.setImageResource(R.drawable.ic_wxpay);
            }
        });
    }
}