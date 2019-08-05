package com.codearms.maoqiqi.one.decoration;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-01 15:45
 */
public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int top;
    private int right;
    private int bottom;
    private int center;

    private int orientation = -1;

    public MarginItemDecoration() {
        this(16);
    }

    public MarginItemDecoration(int margin) {
        this(margin, margin);
    }

    public MarginItemDecoration(int horizontal, int vertical) {
        this(horizontal, vertical, vertical);
    }

    public MarginItemDecoration(int horizontal, int vertical, int center) {
        this(horizontal, vertical, horizontal, vertical, center);
    }

    public MarginItemDecoration(int left, int top, int right, int bottom, int center) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.center = center;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() != null) {
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                orientation = gridLayoutManager.getOrientation();
                setGridLayoutManager(outRect, view, parent, gridLayoutManager.getSpanCount());
            } else if (manager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
                orientation = staggeredGridLayoutManager.getOrientation();
                setStaggeredGridLayoutManager(outRect, view, parent, staggeredGridLayoutManager.getSpanCount());
            } else if (manager instanceof LinearLayoutManager) {
                orientation = ((LinearLayoutManager) manager).getOrientation();
                setLinearLayoutManager(outRect, view, parent);
            }
        }
    }

    private void setGridLayoutManager(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, int spanCount) {
        if (parent.getAdapter() == null) return;

        // 当前位置
        int position = parent.getChildLayoutPosition(view);
        // 总数量
        int count = parent.getAdapter().getItemCount();
        Log.e("info", "position:" + position + ",count:" + count + ",spanCount:" + spanCount);

        if (position % spanCount == 0) {
            // 第一列
            outRect.set(left, 0, center, center);
        } else if ((position + 1) % spanCount == 0) {
            // 最后一列
            outRect.set(0, 0, right, center);
        } else {
            outRect.set(0, 0, center, center);
        }

        // 再判断第一行和最后一行
        if (position < spanCount) {
            // 第一行
            outRect.top = top;
        } else if ((count % spanCount == 0 && position >= count - spanCount)
                || (count % spanCount != 0 && position >= count - count % spanCount)) {
            // 最后一行
            outRect.bottom = bottom;
        }
    }

    private void setStaggeredGridLayoutManager(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, int spanCount) {
        if (parent.getAdapter() == null) return;

        // 当前位置
        int position = parent.getChildLayoutPosition(view);
        // 总数量
        int count = parent.getAdapter().getItemCount();

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex();
        Log.e("info", "position:" + position + ",spanIndex:" + spanIndex);

        outRect.right = right;
        if (spanIndex == 0) {
            outRect.left = left;
        }

//        outRect.set(center, top, center, 0);
//
//        if (spanIndex == 0) {
//            outRect.left=left;
////            outRect.right=center;
////            outRect.set(left, top, center, 0);
//        } else if (spanIndex == spanCount - 1) {
//            outRect.right=center;
////            outRect.set(0, top, right, 0);
//        } else {
//            outRect.right = right;
////            outRect.set(0, top, center, 0);
//        }
////
//        if (spanIndex == 0) {
//            // 第一列
//            outRect.set(center, 0, center, center);
//        } /*else if (spanIndex == spanCount - 1) {
//            // 最后一列
//            outRect.set(0, 0, right, center);
//        } else {
//            outRect.set(0, 0, center, center);
//        }*/
//
//        else {
//            outRect.top=top;
//            outRect.right = right;
//        }

//        // 再判断第一行和最后一行
//        if (position < spanCount) {
//            // 第一行
//            outRect.top = top;
//        } else if ((count % spanCount == 0 && position >= count - spanCount)
//                || (count % spanCount != 0 && position >= count - count % spanCount)) {
//            // 最后一行
//            outRect.bottom = bottom;
//        }
    }

    private void setLinearLayoutManager(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent) {
        if (parent.getAdapter() == null) return;

        int count = parent.getAdapter().getItemCount();
        // 垂直方向
        if (orientation == LinearLayoutManager.VERTICAL) {
            if (parent.getChildLayoutPosition(view) == 0) {
                // 第一项
                outRect.set(left, top, center, center);
            } else if (parent.getChildLayoutPosition(view) == count - 1) {
                // 最后一项
                outRect.set(left, 0, right, bottom);
            } else {
                // 其它
                outRect.set(left, 0, right, center);
            }
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.set(left, top, center, bottom);
            } else if (parent.getChildLayoutPosition(view) == count - 1) {
                outRect.set(0, top, right, bottom);
            } else {
                outRect.set(0, top, center, bottom);
            }
        }
    }
}