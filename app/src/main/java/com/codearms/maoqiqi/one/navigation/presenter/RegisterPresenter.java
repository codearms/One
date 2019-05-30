package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.RegisterContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View registerView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = registerView;
        this.repository = OneRepository.getInstance();
        registerView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void register(String userName, String password, String confirmPassword) {
        compositeDisposable.add(repository.register(userName, password, confirmPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<UserBean>>() {
                    @Override
                    public void onNext(CommonBean<UserBean> commonBean) {
                        if (!registerView.isActive()) return;

                        if (commonBean.getErrorCode() == 0) {
                            registerView.userInfo(commonBean.getData());
                        } else {
                            registerView.showErrorMessage(commonBean.getErrorMsg());
                        }
                    }
                }));
    }
}