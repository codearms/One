package com.codearms.maoqiqi.one.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.codearms.maoqiqi.one.R;

/**
 * 自定义View,用户展示详情页面(标题栏透明度随滚动变化)
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 14:11
 */
public class DetailView extends FrameLayout implements NestedScrollView.OnScrollChangeListener {

    private ImageView ivTitle;
    private Toolbar toolbar;

    private int distance;

    private String expandedText = "展开文字";
    private String collapsedText = "收缩文字";

    public DetailView(@NonNull Context context) {
        this(context, null);
    }

    public DetailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DetailView);
        if (a.getString(R.styleable.DetailView_expanded_text) != null)
            expandedText = a.getString(R.styleable.DetailView_expanded_text);
        if (a.getString(R.styleable.DetailView_collapsed_text) != null)
            collapsedText = a.getString(R.styleable.DetailView_collapsed_text);
        // 回收资源
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 1) {
            View detailView = getChildAt(0);
            removeAllViews();

            NestedScrollView scrollView = new NestedScrollView(getContext());
            scrollView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            scrollView.setOnScrollChangeListener(this);
            scrollView.addView(detailView);

            // 加载视图的布局
            View titleView = View.inflate(getContext(), R.layout.detail_title, null);
            ivTitle = titleView.findViewById(R.id.iv_title);
            toolbar = titleView.findViewById(R.id.toolbar);
            toolbar.setTitle(expandedText);

            int statusBarHeight = getStatusBarHeight();
            int toolbarHeight = toolbar.getLayoutParams().height;

            // 移动的距离
            distance = ivTitle.getLayoutParams().height - (toolbarHeight + statusBarHeight);

            // 使背景图向上移动到图片的最低端,保留(toolbar+statusBar)的高度
            RelativeLayout.LayoutParams ivTitleParams = (RelativeLayout.LayoutParams) ivTitle.getLayoutParams();
            ivTitleParams.topMargin = -distance;
            // 设置透明度
            ivTitle.setAlpha(0f);

            // 设置间距
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin = statusBarHeight;

            addView(scrollView);
            addView(titleView);
        }
    }

    // 根据页面滑动距离改变Header
    @Override
    public void onScrollChange(NestedScrollView view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY < 0) scrollY = 0;

        float alpha = Math.abs(scrollY) * 1.0f / distance;

        if (scrollY <= distance) {
            toolbar.setTitle(expandedText);
            ivTitle.setAlpha(alpha);
        } else {
            toolbar.setTitle(collapsedText);
            ivTitle.setAlpha(1f);
        }
    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    public void setExpandedText(String expandedText) {
        this.expandedText = expandedText;
    }

    public void setCollapsedText(String collapsedText) {
        this.collapsedText = collapsedText;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ImageView getIvTitle() {
        return ivTitle;
    }
}