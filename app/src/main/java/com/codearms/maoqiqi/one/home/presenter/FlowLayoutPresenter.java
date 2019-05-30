package com.codearms.maoqiqi.one.home.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.home.presenter.contract.FlowLayoutContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FlowLayoutPresenter implements FlowLayoutContract.Presenter {

    private FlowLayoutContract.View flowLayoutView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FlowLayoutPresenter(FlowLayoutContract.View flowLayoutView) {
        this.flowLayoutView = flowLayoutView;
        this.repository = OneRepository.getInstance();
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
        compositeDisposable.add(repository.getKnowledge()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<List<ParentClassifyBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<ParentClassifyBean>> commonBean) {
                        if (!flowLayoutView.isActive()) return;

                        flowLayoutView.setKnowledge(commonBean.getData());
                    }
                }));
    }
}