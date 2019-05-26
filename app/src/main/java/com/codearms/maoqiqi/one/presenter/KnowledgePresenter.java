package com.codearms.maoqiqi.one.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.KnowledgeBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.presenter.contract.KnowledgeContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KnowledgePresenter implements KnowledgeContract.Presenter {

    private KnowledgeContract.View knowledgeView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public KnowledgePresenter(KnowledgeContract.View knowledgeView) {
        this.knowledgeView = knowledgeView;
        knowledgeView.setPresenter(this);
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
                .subscribeWith(new DisposableObserver<CommonBean<List<KnowledgeBean>>>() {
                    @Override
                    public void onNext(CommonBean<List<KnowledgeBean>> commonBean) {
                        if (!knowledgeView.isActive()) return;

                        knowledgeView.setKnowledge(commonBean.getData());
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