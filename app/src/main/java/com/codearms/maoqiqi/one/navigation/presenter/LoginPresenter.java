package com.codearms.maoqiqi.one.navigation.presenter;

import com.codearms.maoqiqi.one.home.data.bean.CommonBean;
import com.codearms.maoqiqi.one.home.data.bean.UserBean;
import com.codearms.maoqiqi.one.home.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.navigation.presenter.contract.LoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
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
        compositeDisposable.add(RetrofitManager.getInstance().getServerApi().login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CommonBean<UserBean>>() {
                    @Override
                    public void onNext(CommonBean<UserBean> commonBean) {
                        if (commonBean.getErrorCode() == 0) {
                            loginView.userInfo(commonBean.getData());
                        } else {
                            loginView.showErrorMessage(commonBean.getErrorMsg());
                        }
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