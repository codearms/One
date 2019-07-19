package com.codearms.maoqiqi.one.utils;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-16 15:37
 */
public class OnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                holder.iv_img.setColorFilter(null);
//                hide(holder.tv_date);
//                hide(holder.tv_desc);
//                View parent = (View) holder.itemView.getParent();
//                if (parent != null)
//                    parent.setOnTouchListener(this);
//                break;
//            case MotionEvent.ACTION_UP:
//                holder.iv_img.setColorFilter(Color.parseColor("#5e000000"));
//                show(holder.tv_date);
//                show(holder.tv_desc);
//                break;
//
//        }
        return false;
    }

    void show(View v) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
        alpha.setDuration(300);
        alpha.start();
    }

    void hide(View v) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
        alpha.setDuration(300);
        alpha.start();
    }
}
