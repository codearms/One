package com.codearms.maoqiqi.one.music.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.music.fragment.MusicListFragment;
import com.codearms.maoqiqi.one.music.fragment.MusicMoreFragment;
import com.codearms.maoqiqi.utils.ScreenUtils;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-05 14:03
 */
public class MusicMoreActivity extends BaseActivity {

    private static final String TAG = "com.codearms.maoqiqi.one.MusicMoreFragment";

    public static void start(Context context, MusicSongBean songBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_SONG);
        bundle.putSerializable("songBean", songBean);
        Intent intent = new Intent(context, MusicMoreActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, MusicArtistBean artistBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_ARTIST);
        bundle.putSerializable("artistBean", artistBean);
        Intent intent = new Intent(context, MusicMoreActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, MusicAlbumBean albumBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_ALBUM);
        bundle.putSerializable("albumBean", albumBean);
        Intent intent = new Intent(context, MusicMoreActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, String folderPath) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", MusicListFragment.TYPE_FOLDER);
        bundle.putString("folderPath", folderPath);
        Intent intent = new Intent(context, MusicMoreActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_more);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        int type = bundle.getInt("type");
        MusicMoreFragment fragment = null;
        switch (type) {
            case MusicListFragment.TYPE_SONG:
                MusicSongBean songBean = (MusicSongBean) bundle.getSerializable("songBean");
                fragment = MusicMoreFragment.newInstance(songBean);
                break;
            case MusicListFragment.TYPE_ARTIST:
                MusicArtistBean artistBean = (MusicArtistBean) bundle.getSerializable("artistBean");
                fragment = MusicMoreFragment.newInstance(artistBean);
                break;
            case MusicListFragment.TYPE_ALBUM:
                MusicAlbumBean albumBean = (MusicAlbumBean) bundle.getSerializable("albumBean");
                fragment = MusicMoreFragment.newInstance(albumBean);
                break;
            case MusicListFragment.TYPE_FOLDER:
                String folderPath = bundle.getString("folderPath");
                fragment = MusicMoreFragment.newInstance(folderPath);
                break;
        }
        // fragment = (MusicMoreFragment) getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, TAG).commit();
        }

        View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        view.post(() -> {
            Window window = getWindow();

            window.setWindowAnimations(R.style.DialogSheetAnimation);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            int halfScreenHeight = ScreenUtils.getScreenHeight() / 2;
            params.height = view.getHeight() > halfScreenHeight ?
                    halfScreenHeight : WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        });
    }

    @Override
    public void finish() {
        super.finish();
        // 设置了自己的动画之后,必须重写,解决退出动画无效或者被干扰的问题
        overridePendingTransition(0, 0);
    }

}