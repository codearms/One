package com.codearms.maoqiqi.one.book.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.book.fragment.BookDetailFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 10:38
 */
public class BookDetailActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.BookDetailFragment";

    public static void start(Context context, String id, String title, String imageUrl, Bundle options) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        bundle.putString("imageUrl", imageUrl);
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_book_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String id = bundle.getString("id");
        String title = bundle.getString("title");
        String imageUrl = bundle.getString("imageUrl");

        BookDetailFragment fragment = (BookDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = BookDetailFragment.newInstance(id, title, imageUrl);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }
}