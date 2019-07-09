package com.codearms.maoqiqi.one.navigation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class ProblemFeedbackActivity extends BaseActivity implements View.OnClickListener {

    private static final String QQ_URL = "mqqwpa://im/chat?chat_type=wpa&uin=1335354725";
    private static final String EMAIL_URL = "mailto:fengqi.mao.march@gmail.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_problem_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvIssues = findViewById(R.id.tv_issues);
        TextView tvProblems = findViewById(R.id.tv_problems);
        TextView tvQq = findViewById(R.id.tv_qq);
        TextView tvEmail = findViewById(R.id.tv_email);

        setSupportActionBar(toolbar);

        tvIssues.setOnClickListener(this);
        tvProblems.setOnClickListener(this);
        tvQq.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_issues:
                WebViewActivity.start(this, getString(R.string.issues_url));
                break;
            case R.id.tv_problems:
                WebViewActivity.start(this, getString(R.string.faq_url));
                break;
            case R.id.tv_qq:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(QQ_URL)));
                break;
            case R.id.tv_email:
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(EMAIL_URL)));
                break;
        }
    }
}