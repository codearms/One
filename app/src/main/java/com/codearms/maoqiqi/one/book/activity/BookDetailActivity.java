package com.codearms.maoqiqi.one.book.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.book.fragment.BookDetailFragment;
import com.codearms.maoqiqi.one.utils.AppBarStateChangeListener;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Utils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 10:38
 */
public class BookDetailActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.BookDetailFragment";

    private AppBarLayout appBarLayout;
    private TextView tvTitle;
    private ImageView ivBook;

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

        appBarLayout = findViewById(R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title);
        ivBook = findViewById(R.id.iv_book);

        setSupportActionBar(toolbar);

        Glide.with(this).asBitmap().load(imageUrl).placeholder(R.drawable.ic_book_placeholder).into(
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivBook.setImageBitmap(resource);
                        Utils.setBackground(appBarLayout, resource);
                    }
                });

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) { // 折叠状态
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else if (state == State.EXPANDED) { // 展开状态
                    tvTitle.setText(R.string.book_detail);
                    tvTitle.setVisibility(View.VISIBLE);
                } else {// 中间状态
                    tvTitle.setVisibility(View.INVISIBLE);
                }
            }
        });

        BookDetailFragment fragment = (BookDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = BookDetailFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }
}