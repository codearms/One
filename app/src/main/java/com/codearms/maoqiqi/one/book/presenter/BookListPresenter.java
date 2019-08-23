package com.codearms.maoqiqi.one.book.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.Constants;
import com.codearms.maoqiqi.one.book.presenter.contract.BookListContract;
import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

public class BookListPresenter extends RxPresenterImpl<BookListContract.View> implements BookListContract.Presenter {

    private OneRepository repository;
    private int pageIndex;

    public BookListPresenter(BookListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getBook(String q, String tag, boolean isRefresh) {
        pageIndex = isRefresh ? 0 : pageIndex + 1;
        addSubscribe(repository.getBook(q, tag, pageIndex * Constants.PAGE_COUNT, Constants.PAGE_COUNT)
                .subscribeWith(new BaseObserver<BookListBean>(view) {
                    @Override
                    public void onNext(BookListBean bookListBean) {
                        if (isActive()) view.setBook(bookListBean, isRefresh);
                    }
                }));
    }
}