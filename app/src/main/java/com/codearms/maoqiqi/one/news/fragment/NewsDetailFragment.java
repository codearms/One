package com.codearms.maoqiqi.one.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;
import com.codearms.maoqiqi.one.news.presenter.NewsDetailPresenter;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsDetailContract;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 18:34
 */
public class NewsDetailFragment extends BaseFragment<NewsDetailContract.Presenter> implements NewsDetailContract.View {

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsDetailFragment.
     */
    public static NewsDetailFragment newInstance(int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new NewsDetailPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void setNewsDetail(NewsDetailBean newsDetailBean) {

    }
}