package com.codearms.maoqiqi.one.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.one.ListFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.decoration.MarginItemDecoration;
import com.codearms.maoqiqi.one.news.activity.NewsDetailActivity;
import com.codearms.maoqiqi.one.news.presenter.NewsListPresenter;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-17 14:20
 */
public class NewsListFragment extends ListFragment<NewsListContract.Presenter> implements NewsListContract.View {

    private List<NewsBean.NewsItemBean> list = new ArrayList<>();
    private int topNews;
    private RecyclerAdapter adapter;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsListFragment.
     */
    public static NewsListFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setTag("NewsListFragment-" + category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new NewsListPresenter(this);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        adapter = new RecyclerAdapter(R.layout.item_news_list, list);
        adapter.setTopNews(topNews);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            NewsBean.NewsItemBean item = list.get(position);
            ImageView ivNews = (ImageView) adapter.getViewByPosition(recyclerView, position, R.id.iv_news);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivNews, ViewCompat.getTransitionName(ivNews));
            NewsDetailActivity.start(context, item.getId(), item.getTitle(), options.toBundle());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelSize(R.dimen.twelve)));
    }

    @Override
    protected void loadData() {
        super.loadData();
        refreshLayout.setEnableAutoLoadMore(true);
        // 触发自动刷新
        refreshLayout.autoRefresh();
    }

    @Override
    protected void getData() {
        presenter.getLatestNews(isRefresh);
    }

    @Override
    public void setLatestNews(NewsBean newsBean, boolean isRefresh) {
        loadDataCompleted();
        if (isRefresh) {
            topNews = newsBean.getTopNewsItemBeans().size();

            list.clear();
            list.addAll(newsBean.getTopNewsItemBeans());
            list.addAll(newsBean.getNewsItemBeans());

            adapter.setTopNews(topNews);
            adapter.replaceData(list);

            // 完成刷新,没有更多数据
            refreshLayout.finishRefreshWithNoMoreData();
        }
    }

    final class RecyclerAdapter extends BaseQuickAdapter<NewsBean.NewsItemBean, ViewHolder> {

        private int topNews;

        RecyclerAdapter(int layoutResId, @Nullable List<NewsBean.NewsItemBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, NewsBean.NewsItemBean item) {
            // 置顶
            helper.ivTop.setVisibility(helper.getLayoutPosition() < topNews ? View.VISIBLE : View.GONE);
            String url = item.getImage();
            if (url == null && item.getImages() != null && item.getImages().size() > 0) {
                url = item.getImages().get(0);
            }
            Glide.with(helper.ivNews).load(url).placeholder(R.drawable.ic_news_placeholder).into(helper.ivNews);
            helper.tvTitle.setText(item.getTitle());
        }

        void setTopNews(int topNews) {
            this.topNews = topNews;
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        ImageView ivTop;
        ImageView ivNews;
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTop = itemView.findViewById(R.id.iv_top);
            ivNews = itemView.findViewById(R.id.iv_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}