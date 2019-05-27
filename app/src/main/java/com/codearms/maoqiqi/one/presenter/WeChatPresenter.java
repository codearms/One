package com.codearms.maoqiqi.one.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.presenter.contract.WeChatContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WeChatPresenter implements WeChatContract.Presenter {

    private WeChatContract.View weChatView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeChatPresenter(WeChatContract.View weChatView) {
        this.weChatView = weChatView;
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
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().getWxList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<List<ChildClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ChildClassifyBean>> commonBean) {
                        if (!weChatView.isActive()) return;

                        weChatView.setWxList(commonBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}