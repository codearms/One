package com.codearms.maoqiqi.one.movie.presenter.contract;

import com.codearms.maoqiqi.base.BasePresenter;
import com.codearms.maoqiqi.base.BaseView;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;

public interface MovieListContract {

    interface Presenter extends BasePresenter<View> {

        void getInTheatersMovies(String city, int start, int count);

        void getComingSoonMovies(int start, int count);

        void searchMovie(String q, int start, int count);
    }

    interface View extends BaseView<Presenter> {

        void setData(MovieListBean movieListBean);
    }
}