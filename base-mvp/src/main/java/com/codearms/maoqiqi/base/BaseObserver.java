package com.codearms.maoqiqi.base;

import android.util.Log;

import com.codearms.maoqiqi.execption.ErrorCodeException;

import io.reactivex.observers.ResourceObserver;

public class BaseObserver<T> extends ResourceObserver<T> {

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
                view.showErrorMsg(errorMsgId);
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

    protected boolean isActive() {
        if (view != null && view.isActive()) {
            return true;
        }
        Log.e("info", "Cannot update view.");
        return false;
    }
}