package com.codearms.maoqiqi.one.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.movie.fragment.MovieDetailFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.one.utils.Utils;
import com.codearms.maoqiqi.one.view.DetailView;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 10:38
 */
public class MovieDetailActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.MovieDetailFragment";

    private DetailView detailView;
    private ImageView ivBg;
    private ImageView ivMovie;

    public static void start(Activity activity, String id, String title, String imageUrl, Bundle options) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        bundle.putString("imageUrl", imageUrl);
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(activity, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String id = bundle.getString("id");
        String title = bundle.getString("title");
        String imageUrl = bundle.getString("imageUrl");

        detailView = findViewById(R.id.detail_view);
        ivBg = findViewById(R.id.iv_bg);
        ivMovie = findViewById(R.id.iv_movie);

        detailView.setCollapsedText(title);
        setSupportActionBar(detailView.getToolbar());

        Glide.with(this).asBitmap().load(imageUrl).placeholder(R.drawable.ic_movie_placeholder).into(
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivMovie.setImageBitmap(resource);
                        Utils.setBackground(detailView.getIvTitle(), resource);
                        Utils.setBackground(ivBg, resource);
                    }
                });

        MovieDetailFragment fragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = MovieDetailFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }
    }
}