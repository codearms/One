package com.codearms.maoqiqi.one.data.source;

import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.data.net.ServerApi;
import com.codearms.maoqiqi.one.utils.RxUtils;

import java.util.List;

import io.reactivex.Observable;

public class OneRepository implements OneDataSource {

    private static volatile OneRepository INSTANCE = null;

    private ServerApi api;

    private OneRepository() {
        api = RetrofitManager.getInstance().getServerApi();
    }

    public static OneRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (OneRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OneRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<CommonBean<List<BannerBean>>> getBanner() {
        return api.getBanner().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> getCommon() {
        return api.getCommon().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> getHotKey() {
        return api.getHotKey().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<List<ArticleBean>>> getTopArticles() {
        return api.getTopArticles().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<ArticleBeans>> getArticles(int page) {
        return api.getArticles(page).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<List<NavigationBean>>> getNavigation() {
        return api.getNavigation().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<List<ChildClassifyBean>>> getWxList() {
        return api.getWxList().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<ArticleBeans>> getWxArticles(int id, int page) {
        return api.getWxArticles(id, page).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> getWxSearchArticles(int id, int page, String k) {
        return api.getWxSearchArticles(id, page, k).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<List<ParentClassifyBean>>> getKnowledge() {
        return api.getKnowledge().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<ArticleBeans>> getKnowledgeArticles(int page, int cid) {
        return api.getKnowledgeArticles(page, cid).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<List<ChildClassifyBean>>> getProject() {
        return api.getProject();
    }

    @Override
    public Observable<CommonBean<ArticleBeans>> getProjectArticles(int page, int cid) {
        return api.getProjectArticles(page, cid).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<UserBean>> login(String username, String password) {
        return api.login(username, password).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<UserBean>> register(String username, String password, String repassword) {
        return api.register(username, password, repassword).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> logout() {
        return api.logout().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<ArticleBeans>> getCollect(int page) {
        return api.getCollect(page).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<String>> collect(int id) {
        return api.collect(id).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<String>> collect(String title, String author, String link) {
        return api.collect(title, author, link).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<String>> unCollect(int id) {
        return api.unCollect(id).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean<String>> unCollect(int id, int originId) {
        return api.unCollect(id, originId).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> getCollectUrl() {
        return api.getCollectUrl().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> collectUrl(String name, String link) {
        return api.collectUrl(name, link).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> collectUrl(int id, String name, String link) {
        return api.collectUrl(id, name, link).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> unCollectUrl(int id) {
        return api.unCollectUrl(id).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<CommonBean> query(int page, String k) {
        return api.query(page, k).compose(RxUtils.rxSchedulerHelper());
    }
}