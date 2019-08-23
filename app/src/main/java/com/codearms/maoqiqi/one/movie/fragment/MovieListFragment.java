package com.codearms.maoqiqi.one.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.one.ListFragment;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;
import com.codearms.maoqiqi.one.decoration.DividerItemDecoration;
import com.codearms.maoqiqi.one.movie.activity.MovieDetailActivity;
import com.codearms.maoqiqi.one.movie.presenter.MovieListPresenter;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieListContract;
import com.codearms.maoqiqi.one.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 影视列表
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-08-27 14:00
 */
public class MovieListFragment extends ListFragment<MovieListContract.Presenter> implements MovieListContract.View {

    private int type;
    private String city;
    private String keyword;

    private List<MovieListBean.MovieBean> list = new ArrayList<>();
    private RecyclerAdapter adapter;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieListFragment.
     */
    public static MovieListFragment newInstance(int type, String city, String keyword) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("city", city);
        bundle.putString("keyword", keyword);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new MovieListPresenter(this);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle == null) return;

        type = bundle.getInt("type");
        city = bundle.getString("city");
        keyword = bundle.getString("keyword");

        adapter = new RecyclerAdapter(R.layout.item_movie_list, list);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            MovieListBean.MovieBean item = list.get(position);
            ImageView ivMovie = (ImageView) adapter.getViewByPosition(recyclerView, position, R.id.iv_movie);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivMovie, ViewCompat.getTransitionName(ivMovie));
            MovieDetailActivity.start(context, item.getId(), item.getTitle(), item.getImageBean().getLarge(), options.toBundle());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context));
        recyclerView.setAdapter(adapter);
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
        if (type == 0) {
            presenter.getInTheatersMovies(city, isRefresh);
        } else if (type == 1) {
            presenter.getComingSoonMovies(isRefresh);
        } else if (type == 2) {
            presenter.searchMovie(keyword, isRefresh);
        }
    }

    @Override
    public void setData(MovieListBean movieListBean) {
        loadDataCompleted();
        if (isRefresh) {
            list.clear();
            list.addAll(movieListBean.getMovieBeanList());
            adapter.replaceData(list);

            // 完成刷新
            // Math.ceil两个整数相除,有余数向上取整
            if ((int) Math.ceil((float) movieListBean.getTotal()) / movieListBean.getCount() <= movieListBean.getStart()) {
                refreshLayout.finishRefreshWithNoMoreData();
            } else {
                refreshLayout.finishRefresh();
            }
        } else {
            list.addAll(movieListBean.getMovieBeanList());
            adapter.replaceData(list);

            // 完成加载
            if ((int) Math.ceil((float) movieListBean.getTotal()) / movieListBean.getCount() <= movieListBean.getStart()) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                refreshLayout.finishLoadMore();
            }
        }
    }

    final class RecyclerAdapter extends BaseQuickAdapter<MovieListBean.MovieBean, ViewHolder> {

        RecyclerAdapter(int layoutResId, @Nullable List<MovieListBean.MovieBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, MovieListBean.MovieBean item) {
            Glide.with(helper.ivMovie.getContext()).load(item.getImageBean().getLarge()).placeholder(R.drawable.ic_movie_placeholder).into(helper.ivMovie);

            helper.tvMovieTitle.setText(item.getTitle());
            helper.tvMovieDirector.setText(getString(R.string.movie_director_, MovieUtils.formatPersonName(item.getDirectorsPersonBeanList())));
            helper.tvMovieCast.setText(getString(R.string.movie_star_, MovieUtils.formatPersonName(item.getCastPersonBeanList())));
            helper.tvMovieGenre.setText(getString(R.string.movie_classify, MovieUtils.formatGenre(item.getGenres())));

            if (type == 0) {
                helper.llRating.setVisibility(View.VISIBLE);
                helper.ratingBar.setRating((float) (item.getRatingBean().getAverage() / 2));
                helper.tvMovieRating.setText(String.valueOf(item.getRatingBean().getAverage()));
                helper.tvMovieMainLandPubDate.setVisibility(View.GONE);
            } else {
                helper.llRating.setVisibility(View.GONE);
                helper.tvMovieMainLandPubDate.setVisibility(View.VISIBLE);
                helper.tvMovieMainLandPubDate.setText(getString(R.string.movie_year, item.getMainLandPubDate()));
            }
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        ImageView ivMovie;
        TextView tvMovieTitle;
        TextView tvMovieDirector;
        TextView tvMovieCast;
        TextView tvMovieGenre;
        LinearLayout llRating;
        RatingBar ratingBar;
        TextView tvMovieRating;
        TextView tvMovieMainLandPubDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovie = itemView.findViewById(R.id.iv_movie);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieDirector = itemView.findViewById(R.id.tv_movie_director);
            tvMovieCast = itemView.findViewById(R.id.tv_movie_cast);
            tvMovieGenre = itemView.findViewById(R.id.tv_movie_genre);
            llRating = itemView.findViewById(R.id.ll_rating);
            ratingBar = itemView.findViewById(R.id.rating_rar);
            tvMovieRating = itemView.findViewById(R.id.tv_movie_rating);
            tvMovieMainLandPubDate = itemView.findViewById(R.id.tv_movie_main_land_pub_date);
        }
    }
}