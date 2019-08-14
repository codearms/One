package com.codearms.maoqiqi.base;

import android.widget.Toast;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;

public abstract class BaseFragment<T extends BasePresenter> extends LazyLoadFragment implements BaseView<T> {

    protected T presenter;

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter = null;
        }
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(int resId) {
        hideLoading();
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        hideLoading();
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
    }
}