package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.R;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.SearchContract;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

public class SearchPresenter extends RxPresenterImpl<SearchContract.View> implements SearchContract.Presenter {

    private OneRepository repository;

    public SearchPresenter(SearchContract.View view) {
        super(view);
        repository = OneRepository.getInstance();
    }

    @Override
    public void getHotKey() {
        addSubscribe(repository.getHotKey().subscribeWith(
                new BaseObserver<List<HotKeyBean>>(view, R.string.failed_to_hot_key) {
                    @Override
                    public void onNext(List<HotKeyBean> hotKeyBeans) {
                        if (isActive()) {
                            view.setHotKey(hotKeyBeans);
                            view.setHistory(hotKeyBeans);
                        }
                    }
                }));
    }
}