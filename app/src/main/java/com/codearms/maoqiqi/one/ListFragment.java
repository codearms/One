package com.codearms.maoqiqi.one;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.base.BasePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-22 14:33
 */
public abstract class ListFragment<T extends BasePresenter> extends BaseFragment<T> {

    protected RefreshLayout refreshLayout;
    protected RecyclerView recyclerView;

    protected boolean isRefresh = true;

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        Log.d("info", "-->initViews(@Nullable Bundle savedInstanceState)");
        refreshLayout = rootView.findViewById(R.id.refresh_layout);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        if (refreshLayout == null) return;

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            getData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            getData();
        });
    }

    @Override
    public void showLoading() {
        refreshLayout.setEnableAutoLoadMore(true);
        // 触发自动刷新
        refreshLayout.autoRefresh();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh(false);
        refreshLayout.finishLoadMore(false);
    }

    protected abstract void getData();
}