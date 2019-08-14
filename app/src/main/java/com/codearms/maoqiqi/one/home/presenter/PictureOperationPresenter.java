package com.codearms.maoqiqi.one.home.presenter;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.home.presenter.contract.PictureOperationContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;
import com.codearms.maoqiqi.utils.RxUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-25 18:15
 */
public class PictureOperationPresenter extends RxPresenterImpl<PictureOperationContract.View> implements PictureOperationContract.Presenter {

    public PictureOperationPresenter(PictureOperationContract.View view) {
        super(view);
    }

    @Override
    public void save(String url, Bitmap b) {
        addSubscribe(RxUtils.createData(b)
                .flatMap((Function<Bitmap, ObservableSource<File>>) bitmap -> {
                    File file = saveFile(url, bitmap);
                    if (file != null) {
                        return Observable.just(file);
                    }
                    return Observable.empty();
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<File>(view) {

                    @Override
                    public void onNext(File file) {
                        super.onNext(file);
                        view.saveSuccess(file);
                    }
                }));
    }

    private File saveFile(final String url, final Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), App.getInstance().getString(R.string.app_name));
        if (!appDir.exists()) {
            boolean flag = appDir.mkdir();
        }

        Log.e("info", "url:" + url);
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
}