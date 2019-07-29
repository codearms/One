package com.codearms.maoqiqi.one.home.presenter.contract;

import android.graphics.Bitmap;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;

import java.io.File;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-25 18:14
 */
public interface PictureOperationContract {

    interface Presenter extends BasePresenter<View> {

        void save(String url, Bitmap b);
    }

    interface View extends BaseView<Presenter> {

        void saveSuccess(File file);
    }
}