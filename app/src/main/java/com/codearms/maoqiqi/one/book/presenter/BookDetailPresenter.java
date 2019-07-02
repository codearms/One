package com.codearms.maoqiqi.one.book.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.book.presenter.contract.BookDetailContract;
import com.codearms.maoqiqi.one.data.bean.BookDetailBean;
import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-06-28 11:23
 */
public class BookDetailPresenter extends RxPresenterImpl<BookDetailContract.View> implements BookDetailContract.Presenter {

    private OneRepository repository;

    public BookDetailPresenter(BookDetailContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getBookDetail(String id) {
        addSubscribe(repository.getBookDetail(id)
                .subscribeWith(new BaseObserver<BookDetailBean>(view) {
                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        if (!view.isActive()) return;
                        view.setBookDetail(bookDetailBean);
                    }
                }));
    }
}