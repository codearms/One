package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FlowLayoutPresenter implements FlowLayoutContract.Presenter {

    private FlowLayoutContract.View flowLayoutView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FlowLayoutPresenter(FlowLayoutContract.View flowLayoutView) {
        this.flowLayoutView = flowLayoutView;
        flowLayoutView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getKnowledge();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getKnowledge() {
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().getKnowledge()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<List<ParentClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ParentClassifyBean>> commonBean) {
                        if (!flowLayoutView.isActive()) return;

                        flowLayoutView.setKnowledge(commonBean.getData());
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