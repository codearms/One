package com.codearms.maoqiqi.one.home.fragment;

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

import com.codearms.maoqiqi.lazyload.LazyLoadFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.home.presenter.ArticlesPresenter;
import com.codearms.maoqiqi.one.home.presenter.contract.ArticlesContract;
import com.codearms.maoqiqi.one.navigation.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class ArticlesFragment extends LazyLoadFragment implements ArticlesContract.View {

    private static final String FROM_HOME = "FROM_HOME";
    public static final String FROM_WE_CHAT = "FROM_WE_CHAT";
    public static final String FROM_PROJECT = "FROM_PROJECT";
    public static final String FROM_CLASSIFY = "FROM_CLASSIFY";

    private ArticlesContract.Presenter presenter;

    private RecyclerView recyclerView;

    private String from;
    private int id;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ArticlesFragment.
     */
    public static ArticlesFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString("from", FROM_HOME);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ArticlesFragment newInstance(String from, int id) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putInt("id", id);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setPresenter(ArticlesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ArticlesPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from", FROM_HOME);
            id = bundle.getInt("id");
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        switch (from) {
            case FROM_HOME:
                presenter.subscribe();
                break;
            case FROM_WE_CHAT:
                presenter.getWxArticles(id, 0);
                break;
            case FROM_PROJECT:
                presenter.getProjectArticles(0, id);
                break;
            case FROM_CLASSIFY:
                presenter.getKnowledgeArticles(0, id);
                break;
        }
    }

    private boolean isProject() {
        return from.equals(FROM_PROJECT);
    }

    @Override
    public void setHomeArticles(List<ArticleBean> topArticleBeans, ArticleBeans articleBeans) {
        List<ArticleBean> list = new ArrayList<>();
        list.addAll(topArticleBeans);
        list.addAll(articleBeans.getArticleBeanList());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(topArticleBeans.size(), list));
    }

    @Override
    public void setArticles(ArticleBeans articleBeans) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RecyclerViewAdapter(0, articleBeans.getArticleBeanList()));
    }

    private final class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int topArticles;
        @NonNull
        private List<ArticleBean> ArticleBeanList;
        private final String openProject;
        private final String minute;
        private final String hour;
        private final String oneDay;

        RecyclerViewAdapter(int topArticles, @NonNull List<ArticleBean> ArticleBeanList) {
            this.topArticles = topArticles;
            this.ArticleBeanList = ArticleBeanList;
            openProject = context.getString(R.string.open_project);
            minute = context.getString(R.string.minute);
            hour = context.getString(R.string.hour);
            oneDay = context.getString(R.string.one_day);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final ArticleBean ArticleBean = ArticleBeanList.get(i);
            String superChapterName = ArticleBean.getSuperChapterName();

            viewHolder.ivTop.setVisibility(i < topArticles ? View.VISIBLE : View.GONE);
            viewHolder.tvTitle.setText(ArticleBean.getTitle());

            if (ArticleBean.getNiceDate().contains(minute)
                    || ArticleBean.getNiceDate().contains(hour)
                    || ArticleBean.getNiceDate().contains(oneDay)) {
                viewHolder.tvNew.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvNew.setVisibility(View.GONE);
            }

            if (isProject()) {
                viewHolder.tvProject.setVisibility(View.GONE);
            } else {
                viewHolder.tvProject.setVisibility(superChapterName.contains(openProject) ? View.VISIBLE : View.GONE);
            }

            viewHolder.tvClassify.setText(String.format("%s/%s", superChapterName, ArticleBean.getChapterName()));
            viewHolder.tvAuthor.setText(ArticleBean.getAuthor());
            viewHolder.tvDate.setText(ArticleBean.getNiceDate());

            if (i == ArticleBeanList.size() - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHolder.cardView.getLayoutParams();
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sixteen);
            }
            viewHolder.cardView.setOnClickListener(v -> WebViewActivity.start(context, ArticleBean.getLink(), 0));
        }

        @Override
        public int getItemCount() {
            return ArticleBeanList.size();
        }
    }

    private final class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView ivTop;
        TextView tvTitle;
        TextView tvNew;
        TextView tvProject;
        TextView tvClassify;
        TextView tvAuthor;
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            ivTop = itemView.findViewById(R.id.iv_top);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNew = itemView.findViewById(R.id.tv_new);
            tvClassify = itemView.findViewById(R.id.tv_classify);
            tvProject = itemView.findViewById(R.id.tv_project);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}