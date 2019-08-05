package com.codearms.maoqiqi.one.music.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.music.fragment.MusicListFragment;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.ScreenUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-04 14:34
 */
public class MusicListActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.MusicListFragment";

    private CompositeDisposable compositeDisposable;

    public static void start(Context context, String title, String imageUrl, Bundle options, long artistId, long albumId, String folderPath) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("imageUrl", imageUrl);
        bundle.putLong("artistId", artistId);
        bundle.putLong("albumId", albumId);
        bundle.putString("folderPath", folderPath);
        Intent intent = new Intent(context, MusicListActivity.class);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreen(this);
        setContentView(R.layout.activity_music_list);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String title = bundle.getString("title");
        String imageUrl = bundle.getString("imageUrl");
        long artistId = bundle.getLong("artistId");
        long albumId = bundle.getLong("albumId");
        String folderPath = bundle.getString("folderPath", "");

        int resourceId = R.drawable.ic_song_placeholder;
        if (artistId != 0) {
            resourceId = R.drawable.ic_artist_placeholder;
        } else if (albumId != 0) {
            resourceId = R.drawable.ic_album_placeholder;
        } else if (folderPath != null && !folderPath.equals("")) {
            resourceId = R.drawable.ic_folder_placeholder;
        }

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.getLayoutParams().height = ScreenUtils.getScreenWidth();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ImageView ivHeader = findViewById(R.id.iv_header);
        Glide.with(this).load(imageUrl).placeholder(resourceId).into(ivHeader);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
                    MusicListFragment fragment = (MusicListFragment) getSupportFragmentManager().findFragmentByTag(TAG);
                    if (fragment == null) {
                        fragment = MusicListFragment.newInstance(artistId, albumId, folderPath);
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}