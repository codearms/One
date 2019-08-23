package com.codearms.maoqiqi.one.movie.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;

public interface MovieListContract {

    interface Presenter extends BasePresenter<View> {

        void getInTheatersMovies(String city, boolean isRefresh);

        void getComingSoonMovies(boolean isRefresh);

        void searchMovie(String q, boolean isRefresh);
    }

    interface View extends BaseView<Presenter> {

        void setData(MovieListBean movieListBean);
    }
}