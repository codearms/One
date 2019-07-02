package com.codearms.maoqiqi.one.movie.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.movie.presenter.contract.MovieListContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class MovieListPresenter extends RxPresenterImpl<MovieListContract.View> implements MovieListContract.Presenter {

    private OneRepository repository;

    public MovieListPresenter(MovieListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getInTheatersMovies(String city, int start, int count) {
        addSubscribe(repository.inTheatersMovies(city, start, count)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {

                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (!view.isActive()) return;
                        view.setData(movieListBean);
                    }
                }));
    }

    @Override
    public void getComingSoonMovies(int start, int count) {
        addSubscribe(repository.comingSoonMovies(start, count)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {

                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (!view.isActive()) return;
                        view.setData(movieListBean);
                    }
                }));
    }

    @Override
    public void searchMovie(String q, int start, int count) {
        addSubscribe(repository.searchMovie(q, start, count)
                .subscribeWith(new BaseObserver<MovieListBean>(view) {

                    @Override
                    public void onNext(MovieListBean movieListBean) {
                        if (!view.isActive()) return;
                        view.setData(movieListBean);
                    }
                }));
    }
}