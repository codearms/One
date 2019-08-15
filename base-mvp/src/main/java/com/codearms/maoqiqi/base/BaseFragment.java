package com.codearms.maoqiqi.base;

import android.widget.Toast;

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.loading.LoadingFragment;

public abstract class BaseFragment<T extends BasePresenter> extends LazyLoadFragment implements BaseView<T> {

    private static final String TAG = "com.codearms.maoqiqi.base.LoadingFragment";

    protected T presenter;

    private LoadingFragment fragment;

    // 视图被销毁,如果设置了setRetainInstance(true),依然会继续这个异步任务.
    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
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
        if (fragment == null) {
            fragment = (LoadingFragment) getChildFragmentManager().findFragmentByTag(TAG);
        }
        if (fragment == null) {
            fragment = LoadingFragment.newInstance();
        }
        fragment.show(getChildFragmentManager(), TAG);
    }

    @Override
    public void hideLoading() {
        if (fragment != null) {
            fragment.dismiss();
        }
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