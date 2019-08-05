package com.codearms.maoqiqi.one.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * RecyclerView的分割线
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-01 14:50
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private int orientation = -1;

    private Drawable divider;
    private int width;
    private int height;

    public DividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();

        setDivider(divider);
    }

    private void setDivider(Drawable divider) {
        if (divider == null) {
            this.divider = new ColorDrawable(Color.LTGRAY);
            this.width = 2;
            this.height = 2;
        } else {
            this.divider = divider;
            this.width = divider.getIntrinsicWidth();
            this.height = divider.getIntrinsicHeight();
        }
        Log.e("info", "width:" + width + ",height:" + height);
    }

    private void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == -1 && parent.getLayoutManager() != null) {
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                orientation = ((LinearLayoutManager) manager).getOrientation();
            }
        }

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.bottom = height;
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.right = width;
        }
    }

    // 绘制分割线
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        }
    }

    // 绘制横向分割线
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + height;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    // 绘制纵向分割线
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + width;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }
}