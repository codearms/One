package com.codearms.maoqiqi.one.book.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.book.presenter.contract.BookListContract;
import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class BookListPresenter extends RxPresenterImpl<BookListContract.View> implements BookListContract.Presenter {

    private OneRepository repository;

    public BookListPresenter(BookListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getBook(String q, String tag, int start, int count) {
        addSubscribe(repository.getBook(q, tag, start, count)
                .subscribeWith(new BaseObserver<BookListBean>(view) {

                    @Override
                    public void onNext(BookListBean bookListBean) {
                        if (!view.isActive()) return;
                        view.setBook(bookListBean);
                    }
                }));
    }
}