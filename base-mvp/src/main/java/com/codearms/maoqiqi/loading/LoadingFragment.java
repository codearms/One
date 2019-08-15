package com.codearms.maoqiqi.loading;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.codearms.maoqiqi.base.BaseDialogFragment;
import com.codearms.maoqiqi.base.R;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-15 13:59
 */
public class LoadingFragment extends BaseDialogFragment {

    public static LoadingFragment newInstance() {
        return new LoadingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(() -> {
            if (getDialog() == null || getDialog().getWindow() == null) return;

            if (getDialog() != null) {
                getDialog().setCanceledOnTouchOutside(false);
                if (getDialog().getWindow() != null) {
                    Window window = getDialog().getWindow();
                    window.setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams params = window.getAttributes();
                    int n = getScreenWidth() / 3;
                    params.width = n;
                    params.height = n;
                    params.gravity = Gravity.CENTER;
                    window.setAttributes(params);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        // 不假这段代码,屏幕旋转会消失
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    // Getting screen height
    private int getScreenWidth() {
        if (getActivity() == null) return 600;
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                outMetrics.widthPixels : outMetrics.heightPixels;
    }
}