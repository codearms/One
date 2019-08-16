package com.codearms.maoqiqi.one.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.ListFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.decoration.MarginItemDecoration;
import com.codearms.maoqiqi.one.home.activity.ClassifyActivity;
import com.codearms.maoqiqi.one.home.activity.PictureActivity;
import com.codearms.maoqiqi.one.home.adapter.ArticlesAdapter;
import com.codearms.maoqiqi.one.home.presenter.ArticlesPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文章列表
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-07 11:30
 */
public class ArticlesFragment extends ListFragment<ArticlesContract.Presenter> implements ArticlesContract.View {

    public static final String FROM_HOME = "FROM_HOME";
    public static final String FROM_WE_CHAT = "FROM_WE_CHAT";
    public static final String FROM_PROJECT = "FROM_PROJECT";
    public static final String FROM_CLASSIFY = "FROM_CLASSIFY";
    public static final String FROM_COLLECT = "FROM_COLLECT";
    public static final String FROM_SEARCH = "FROM_SEARCH";

    private List<ArticleBean> list = new ArrayList<>();
    private int topArticles;
    private ArticlesAdapter adapter;

    // 来自哪一个页面
    private String from;
    private int id;
    private String k;
    private boolean isClassify;

    // 记录点击的文章位置,便于在文章内点击收藏返回到此界面时能展示正确的收藏状态
    private int operationPosition = -1;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param from       来自哪个页面
     * @param id         公众号Id或者分类的Id
     * @param isClassify 是否能再次打开分类页面 true:不能 false:能
     * @return A new instance of fragment ArticlesFragment.
     */
    public static ArticlesFragment newInstance(String from, int id, boolean isClassify) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putInt("id", id);
        bundle.putBoolean("isClassify", isClassify);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param from 来自哪个页面
     * @param k    搜索关键字
     * @return A new instance of fragment ArticlesFragment.
     */
    public static ArticlesFragment newInstance(String from, String k) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("k", k);
        bundle.putBoolean("isClassify", false);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new ArticlesPresenter(this);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from", FROM_HOME);
            id = bundle.getInt("id");
            k = bundle.getString("k");
            isClassify = bundle.getBoolean("isClassify", true);
        }

        // 首页不需要
        if (from.equals(FROM_HOME)) {
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setEnableLoadMore(false);
        }

        adapter = new ArticlesAdapter(R.layout.item_articles, list, topArticles, from, isClassify);
        adapter.setOnItemChildClickListener((adapter, view, position) -> itemChildClick(view, position));

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginItemDecoration(getResources().getDimensionPixelSize(R.dimen.twelve)));

        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
    }

    private void itemChildClick(View view, int position) {
        ArticleBean bean = adapter.getData().get(position);
        switch (view.getId()) {
            case R.id.card_view:
                operationPosition = position;
                if (from.equals(FROM_COLLECT)) {
                    WebViewActivity.start(context, from, bean);
                } else {
                    WebViewActivity.start(context, 0, from, bean);
                }
                break;
            case R.id.iv_project:
                PictureActivity.start(context, "", null);
                break;
            case R.id.iv_collect:
                // 判断是否登录
                if (!App.getInstance().isLogin()) {
                    showErrorMsg(getString(R.string.please_login));
                    return;
                }

                operationPosition = position;
                // 收藏页面
                if (from.equals(FROM_COLLECT)) {
                    presenter.unCollect(bean.getId(), bean.getOriginId());
                } else {
                    // 是否收藏
                    if (bean.isCollect()) {
                        presenter.unCollect(bean.getId());
                    } else {
                        presenter.collect(bean.getId());
                    }
                }
                break;
            case R.id.tv_classify:
                // 跳转到分类
                ClassifyActivity.start(context, from, bean);
                break;
        }
    }

    @Override
    protected void onVisibleChange(boolean isVisible) {
        super.onVisibleChange(isVisible);
        if (!from.equals(FROM_HOME)) {
            if (isVisible && !isLoadDataCompleted()) {
                showLoading();
            } else {
                hideLoading();
            }
        }
    }

    // refreshLayout.autoRefresh();会调用loadData()方法,所以不能自己再次调用
    @Override
    protected void loadData() {
        super.loadData();
        if (from.equals(FROM_HOME)) getData();
    }

    @Override
    protected void getData() {
        switch (from) {
            case FROM_HOME:
                // 首页数据
                presenter.getHomeArticles();
                break;
            case FROM_WE_CHAT:
                // 查看某个公众号历史数据
                presenter.getWxArticles(id, isRefresh);
                break;
            case FROM_PROJECT:
                // 项目列表数据
                presenter.getProjectArticles(id, isRefresh);
                break;
            case FROM_CLASSIFY:
                // 知识体系下的文章
                presenter.getKnowledgeArticles(id, isRefresh);
                break;
            case FROM_COLLECT:
                // 收藏文章列表
                presenter.getCollect(isRefresh);
                break;
            case FROM_SEARCH:
                // 搜索
                presenter.query(0, k);
                break;
        }
    }

    public void setSearchData(String k) {
        // 内容相同,不去搜索
        if (this.k.equals(k)) return;
        this.k = k;
        presenter.query(0, k);
    }

    @Override
    public void setHomeArticles(List<ArticleBean> topArticleBeans, ArticleBeans articleBeans) {
        loadDataCompleted();
        list.addAll(topArticleBeans);
        list.addAll(articleBeans.getArticleBeanList());

        topArticles = topArticleBeans.size();
        adapter.setTopArticles(topArticles);
        adapter.replaceData(list);
    }

    @Override
    public void setArticles(ArticleBeans articleBeans, boolean isRefresh) {
        loadDataCompleted();
        if (isRefresh) {
            list = articleBeans.getArticleBeanList();
            adapter.replaceData(list);

            // 完成刷新
            if (articleBeans.getPageCount() <= articleBeans.getCurPage()) {
                refreshLayout.finishRefreshWithNoMoreData();
            } else {
                refreshLayout.finishRefresh();
            }
        } else {
            list.addAll(articleBeans.getArticleBeanList());
            adapter.replaceData(list);

            // 完成加载
            if (articleBeans.getPageCount() <= articleBeans.getCurPage()) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void collectSuccess() {
        ArticleBean bean = adapter.getData().get(operationPosition);
        bean.setCollect(true);
        adapter.setData(operationPosition, bean);
        showErrorMsg(getString(R.string.success_to_collect));
    }

    @Override
    public void unCollectSuccess() {
        if (from.equals(FROM_COLLECT)) {
            // 移除
            adapter.remove(operationPosition);
            if (operationPosition == adapter.getData().size()) {
                adapter.notifyItemChanged(operationPosition - 1);
            }
        } else {
            // 更新
            ArticleBean bean = adapter.getData().get(operationPosition);
            bean.setCollect(false);
            adapter.setData(operationPosition, bean);
        }
        showErrorMsg(getString(R.string.success_to_un_collect));
    }
}