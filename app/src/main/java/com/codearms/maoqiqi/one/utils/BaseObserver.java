package com.codearms.maoqiqi.one.utils;

import android.util.Log;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.App;

import io.reactivex.observers.ResourceObserver;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private BaseView<? extends BasePresenter> view;
    private int errorMsgId;

    public BaseObserver(BaseView<? extends BasePresenter> view) {
        this.view = view;
    }

    public BaseObserver(BaseView<? extends BasePresenter> view, int errorMsgId) {
        this.view = view;
        this.errorMsgId = errorMsgId;
    }

    @Override
    public void onNext(T t) {
        Log.e("info", "onNext()");
    }

    @Override
    public void onError(Throwable e) {
        if (view == null) return;

        if (e instanceof ErrorCodeException) {
            if (errorMsgId != 0) {
                view.showErrorMsg(App.getInstance().getString(errorMsgId));
            } else {
                view.showErrorMsg(((ErrorCodeException) e).getErrorMsg());
            }
        } else {
            Log.e("info", e.getMessage());
            view.showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        Log.e("info", "onComplete()");
    }
}