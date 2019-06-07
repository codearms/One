package com.codearms.maoqiqi.one.navigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class ProblemFeedbackActivity extends BaseActivity implements View.OnClickListener {

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

    }
}