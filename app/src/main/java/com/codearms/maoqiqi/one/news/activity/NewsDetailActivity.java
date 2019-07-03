package com.codearms.maoqiqi.one.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.news.fragment.NewsDetailFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 18:17
 */
public class NewsDetailActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.NewsDetailFragment";

    public static void start(Context context, int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_news_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        
        int id = bundle.getInt("id");
        String title = bundle.getString("title");

        NewsDetailFragment fragment = (NewsDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = NewsDetailFragment.newInstance(id, title);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }
}