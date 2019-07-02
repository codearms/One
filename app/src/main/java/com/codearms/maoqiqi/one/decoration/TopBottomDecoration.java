package com.codearms.maoqiqi.one.decoration;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TopBottomDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public TopBottomDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = space;
        } else if (parent.getChildLayoutPosition(view) == state.getItemCount() - 1) {
            outRect.right = space;
        }
    }
}