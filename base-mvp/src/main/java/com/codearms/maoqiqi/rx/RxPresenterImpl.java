package com.codearms.maoqiqi.rx;

import com.codearms.maoqiqi.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class RxPresenterImpl<T extends BaseView> implements RxPresenter<T> {

    protected T view;

    private CompositeDisposable compositeDisposable;

    public RxPresenterImpl(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }
}