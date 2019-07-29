package com.codearms.maoqiqi.base;

import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.Toast;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-26 14:07
 */
public abstract class BaseBottomSheetDialogFragment<T extends BasePresenter> extends BottomSheetDialogFragment implements BaseView<T> {

    protected Context context;

    protected T presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

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
        context = null;
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
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}