package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.base.RxPresenterImpl;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.WeChatContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeChatPresenter extends RxPresenterImpl<WeChatContract.View> implements WeChatContract.Presenter {

    private OneRepository repository;

    public WeChatPresenter(WeChatContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getWxList() {
        addSubscribe(repository.getWxList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<List<ChildClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!view.isActive()) return;

                        view.setWxList(commonBean.getData());
                    }
                }));
    }
}