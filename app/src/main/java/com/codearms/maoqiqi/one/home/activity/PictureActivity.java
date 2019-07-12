package com.codearms.maoqiqi.one.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codearms.maoqiqi.base.BaseActivity;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.RxUtils;
import com.codearms.maoqiqi.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;

public class PictureActivity extends BaseActivity {

    private CompositeDisposable compositeDisposable;
    private boolean showing;
    private Bitmap bitmap;

    public static void start(@NonNull Context context, String url, Bundle options) {
        Bundle bundle = new Bundle();
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

        String url = bundle.getString("url");

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView ivImage = findViewById(R.id.iv_image);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ivImage.setOnClickListener(v -> {
            showing = !showing;
            appBarLayout.setVisibility(showing ? View.VISIBLE : View.GONE);
        });
        ivImage.setOnLongClickListener(v -> {
            ToastUtils.show("保存图片");
            save(url, bitmap);
            return true;
        });
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.ic_belle_placeholder).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap = resource;
                ivImage.setImageBitmap(bitmap);
            }
        });

        compositeDisposable = new CompositeDisposable();
    }

    private void save(final String url, final Bitmap b) {
        if (bitmap == null) {
            ToastUtils.show("无法下载");
            return;
        }

        ResourceObserver<File> observer = new ResourceObserver<File>() {
            @Override
            public void onNext(File file) {
                notifyGalleryUpdate(PictureActivity.this, file);
                ToastUtils.show(getString(R.string.save_folder, file.getAbsolutePath()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        compositeDisposable.add(RxUtils.createData(b)
                .flatMap((Function<Bitmap, ObservableSource<File>>) bitmap -> {
                    File file = saveFile(url, bitmap);
                    if (file != null) {
                        return Observable.just(file);
                    }
                    return Observable.empty();
                }).compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(observer));
    }

    private File saveFile(final String url, final Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        if (!appDir.exists()) {
            boolean flag = appDir.mkdir();
        }

        String fileName = url.substring(url.lastIndexOf("/"));
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void notifyGalleryUpdate(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_share) {
            ToastUtils.show("share");
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}