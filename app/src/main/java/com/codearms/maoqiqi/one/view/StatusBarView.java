package com.codearms.maoqiqi.one.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.codearms.maoqiqi.one.utils.StatusBarUtils;
import com.codearms.maoqiqi.utils.ActivityUtils;

/**
 * StatusBarView 适配状态栏
 * Author: fengqi.mao.march@gmail.com
 * Date: 2018/8/20 10:48
 */
public class StatusBarView extends View {

    public StatusBarView(Context context) {
        super(context);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
        setMeasuredDimension(widthMeasureSpec, StatusBarUtils.getStatusBarHeight(getContext()));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window window = ActivityUtils.getActivityByContext(getContext()).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            params.flags |= bits;
        } else {
            params.flags &= ~bits;
        }
        window.setAttributes(params);
    }
}