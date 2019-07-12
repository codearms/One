package com.codearms.maoqiqi.one.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;

public class PictureActivity extends BaseActivity {

    private boolean showing;

    public static void start(@NonNull Context context, String title, String url, Bundle options) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_picture);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String title = bundle.getString("title");
        String url = bundle.getString("url");

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView ivImage = findViewById(R.id.iv_image);

        if (title != null && !title.equals("")) toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ivImage.setOnClickListener(v -> {
            showing = !showing;
            appBarLayout.setVisibility(showing ? View.VISIBLE : View.GONE);
        });
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.ic_belle_placeholder).into(ivImage);
    }
}