package com.codearms.maoqiqi.one.movie.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;
import com.codearms.maoqiqi.one.decoration.DividerDecoration;
import com.codearms.maoqiqi.one.movie.activity.MovieDetailActivity;
import com.codearms.maoqiqi.one.movie.presenter.MovieListPresenter;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieListContract;
import com.codearms.maoqiqi.one.utils.MovieUtils;

import java.util.List;

public class MovieListFragment extends BaseFragment<MovieListContract.Presenter> implements MovieListContract.View {

    private List<MovieListBean.MovieBean> list;
    private RecyclerAdapter adapter;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieListFragment.
     */
    public static MovieListFragment newInstance(int type, String city, String keyword, String movieTag) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("city", city);
        bundle.putString("keyword", keyword);
        bundle.putString("movieTag", movieTag);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovieListPresenter(this);
    }

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        int type = getArguments().getInt("type");

        adapter = new RecyclerAdapter(R.layout.item_movie_list, list, type);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieListBean.MovieBean item = list.get(position);
                ImageView ivMovie = (ImageView) adapter.getViewByPosition(recyclerView, position, R.id.iv_movie);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivMovie, ViewCompat.getTransitionName(ivMovie));
                MovieDetailActivity.start(getActivity(), item.getId(), item.getTitle(), item.getImageBean().getLarge(), options.toBundle());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerDecoration());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter.getInTheatersMovies("上海", Constants.PAGE_INDEX, Constants.PAGE_COUNT);
    }

    @Override
    public void setData(MovieListBean movieListBean) {
        list = movieListBean.getMovieBeanList();
        adapter.replaceData(list);
    }

    static final class RecyclerAdapter extends BaseQuickAdapter<MovieListBean.MovieBean, ViewHolder> {

        private int type;
        private final Resources resources;

        RecyclerAdapter(int layoutResId, @Nullable List<MovieListBean.MovieBean> data, int type) {
            super(layoutResId, data);
            this.type = type;
            this.resources = App.getInstance().getResources();
        }

        @Override
        protected void convert(ViewHolder helper, MovieListBean.MovieBean item) {
            Glide.with(helper.ivMovie.getContext()).load(item.getImageBean().getLarge()).placeholder(R.drawable.ic_movie_placeholder).into(helper.ivMovie);

            helper.tvMovieTitle.setText(item.getTitle());
            helper.tvMovieDirector.setText(resources.getString(R.string.movie_director_, MovieUtils.formatPersonName(item.getDirectorsPersonBeanList())));
            helper.tvMovieCast.setText(resources.getString(R.string.movie_star_, MovieUtils.formatPersonName(item.getCastPersonBeanList())));
            helper.tvMovieGenre.setText(resources.getString(R.string.movie_classify, MovieUtils.formatGenre(item.getGenres())));

            if (type == 0) {
                helper.llRating.setVisibility(View.VISIBLE);
                helper.ratingBar.setRating((float) (item.getRatingBean().getAverage() / 2));
                helper.tvMovieRating.setText(String.valueOf(item.getRatingBean().getAverage()));
            } else {
                helper.llRating.setVisibility(View.GONE);
            }
        }
    }

    static final class ViewHolder extends BaseViewHolder {

        LinearLayout llItem;
        ImageView ivMovie;
        TextView tvMovieTitle;
        TextView tvMovieDirector;
        TextView tvMovieCast;
        TextView tvMovieGenre;
        RatingBar ratingBar;
        TextView tvMovieRating;
        LinearLayout llRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            ivMovie = itemView.findViewById(R.id.iv_movie);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieDirector = itemView.findViewById(R.id.tv_movie_director);
            tvMovieCast = itemView.findViewById(R.id.tv_movie_cast);
            tvMovieGenre = itemView.findViewById(R.id.tv_movie_genre);
            ratingBar = itemView.findViewById(R.id.rating_rar);
            tvMovieRating = itemView.findViewById(R.id.tv_movie_rating);
            llRating = itemView.findViewById(R.id.ll_rating);
        }
    }
}