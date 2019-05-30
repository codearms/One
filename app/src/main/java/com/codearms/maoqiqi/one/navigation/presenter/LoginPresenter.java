package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.navigation.presenter.contract.LoginContract;
import com.codearms.maoqiqi.one.utils.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private OneRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        this.repository = OneRepository.getInstance();
        loginView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void login(String userName, String password) {
        compositeDisposable.add(repository.login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<CommonBean<UserBean>>() {
                    @Override
                    public void onNext(CommonBean<UserBean> commonBean) {
                        if (commonBean.getErrorCode() == 0) {
                            loginView.userInfo(commonBean.getData());
                        } else {
                            loginView.showErrorMessage(commonBean.getErrorMsg());
                        }
                    }
                }));
    }
}