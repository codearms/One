package com.codearms.maoqiqi.one.navigation.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.util.List;

/**
 * 问题反馈
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-06 15:00
 */
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
        switch (v.getId()) {
            case R.id.tv_issues:
                WebViewActivity.start(this, getString(R.string.issues_url));
                break;
            case R.id.tv_problems:
                WebViewActivity.start(this, getString(R.string.problems_url));
                break;
            case R.id.tv_qq:
                String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=1335354725";
                if (isQQClientAvailable()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                } else {
                    ToastUtils.show("当前设备未安装QQ");
                }
                break;
            case R.id.tv_email:
                String emailUrl = "mailto:fengqi.mao.march@gmail.com";
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(emailUrl)));
                break;
        }
    }

    /**
     * 判断QQ是否可用
     *
     * @return true:可用 false:不可用
     */
    public boolean isQQClientAvailable() {
        final PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String name = packageInfo.get(i).packageName;
                if (name.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}