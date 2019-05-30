package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.WeChatContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatPresenter implements WeChatContract.Presenter {

    private WeChatContract.View weChatView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeChatPresenter(WeChatContract.View weChatView) {
        this.weChatView = weChatView;
        this.repository = OneRepository.getInstance();
        weChatView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getWxList();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getWxList() {
        compositeDisposable.add(repository.getWxList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<List<ChildClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!weChatView.isActive()) return;

                        weChatView.setWxList(commonBean.getData());
                    }
                }));
    }
}