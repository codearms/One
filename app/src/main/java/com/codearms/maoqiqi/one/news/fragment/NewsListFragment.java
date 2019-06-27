package com.codearms.maoqiqi.one.news.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.news.activity.NewsDetailActivity;
import com.codearms.maoqiqi.one.news.presenter.NewsListPresenter;
import com.codearms.maoqiqi.one.news.presenter.contract.NewsListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-27 14:20
 */
public class NewsListFragment extends BaseFragment<NewsListContract.Presenter> implements NewsListContract.View {

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
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        adapter = new RecyclerAdapter(R.layout.item_news_list, list);
        adapter.setTopNews(topNews);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getLatestNews();
    }

    @Override
    public void setLatestNews(NewsBean newsBean) {
        topNews = newsBean.getTopNewsItemBeans().size();

        list.clear();
        list.addAll(newsBean.getTopNewsItemBeans());
        list.addAll(newsBean.getNewsItemBeans());

        adapter.setTopNews(topNews);
        adapter.replaceData(list);
    }

    static final class RecyclerAdapter extends BaseQuickAdapter<NewsBean.NewsItemBean, ViewHolder> {

        private int topNews;
        private final Application application;

        RecyclerAdapter(int layoutResId, @Nullable List<NewsBean.NewsItemBean> data) {
            super(layoutResId, data);
            this.application = App.getInstance();
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
            helper.cardView.setOnClickListener(v -> NewsDetailActivity.start(v.getContext(), item.getId(), item.getTitle()));

            // 设置间距
            if (helper.getLayoutPosition() == getItemCount() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.cardView.getLayoutParams();
                params.bottomMargin = application.getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
        }

        void setTopNews(int topNews) {
            this.topNews = topNews;
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        CardView cardView;
        ImageView ivTop;
        ImageView ivNews;
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            ivTop = itemView.findViewById(R.id.iv_top);
            ivNews = itemView.findViewById(R.id.iv_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}