package com.codearms.maoqiqi.one.data.source;

import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.BookDetailBean;
import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.DataBean;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;
import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.bean.UsefulSitesBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;
import com.codearms.maoqiqi.one.data.net.DouBanApi;
import com.codearms.maoqiqi.one.data.net.GankApi;
import com.codearms.maoqiqi.one.data.net.NewsAPI;
import com.codearms.maoqiqi.one.data.net.RetrofitManager;
import com.codearms.maoqiqi.one.data.net.ServerApi;
import com.codearms.maoqiqi.one.utils.MediaLoader;
import com.codearms.maoqiqi.utils.RxUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class OneRepository implements OneDataSource {

    private static volatile OneRepository INSTANCE = null;

    private ServerApi api;
    private NewsAPI newsAPI;
    private DouBanApi douBanApi;
    private GankApi gankApi;

    private OneRepository() {
        api = RetrofitManager.getInstance().getServerApi();
        newsAPI = RetrofitManager.getInstance().getNewsAPI();
        douBanApi = RetrofitManager.getInstance().getDouBanApi();
        gankApi = RetrofitManager.getInstance().getGankApi();
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
    public Observable<List<BannerBean>> getBanner() {
        return api.getBanner().delay(3000, TimeUnit.MILLISECONDS).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<UsefulSitesBean>> getUsefulSites() {
        return api.getUsefulSites().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<HotKeyBean>> getHotKey() {
        return api.getHotKey().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<ArticleBean>> getTopArticles() {
        return api.getTopArticles().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> getArticles(int page) {
        return api.getArticles(page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<NavigationBean>> getNavigation() {
        return api.getNavigation().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<ChildClassifyBean>> getWxList() {
        return api.getWxList().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> getWxArticles(int id, int page) {
        return api.getWxArticles(id, page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> getWxSearchArticles(int id, int page, String k) {
        return api.getWxSearchArticles(id, page, k).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<ParentClassifyBean>> getKnowledge() {
        return api.getKnowledge().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> getKnowledgeArticles(int page, int cid) {
        return api.getKnowledgeArticles(page, cid).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<List<ChildClassifyBean>> getProject() {
        return api.getProject().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> getProjectArticles(int page, int cid) {
        return api.getProjectArticles(page, cid).delay(3000, TimeUnit.MILLISECONDS).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<UserBean> login(String username, String password) {
        return api.login(username, password).delay(8000, TimeUnit.MILLISECONDS).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<UserBean> register(String username, String password, String repassword) {
        return api.register(username, password, repassword).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> logout() {
        return api.logout().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> getCollect(int page) {
        return api.getCollect(page).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> collect(int id) {
        return api.collect(id).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBean> collect(String title, String author, String link) {
        return api.collect(title, author, link).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> unCollect(int id) {
        return api.unCollect(id).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> unCollect(int id, int originId) {
        return api.unCollect(id, originId).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> getCollectUrl() {
        return api.getCollectUrl().compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> collectUrl(String name, String link) {
        return api.collectUrl(name, link).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> collectUrl(int id, String name, String link) {
        return api.collectUrl(id, name, link).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<Object> unCollectUrl(int id) {
        return api.unCollectUrl(id).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<ArticleBeans> query(int page, String k) {
        return api.query(page, k).compose(RxUtils.rxSchedulerHelper()).compose(RxUtils.handleResult());
    }

    @Override
    public Observable<NewsBean> getLatestNews() {
        return newsAPI.getLatestNews().compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<NewsDetailBean> getNewsDetail(int id) {
        return newsAPI.getNewsDetail(id).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<BookListBean> getBook(String q, String tag, int start, int count) {
        return douBanApi.getBook("0df993c66c0c636e29ecbb5344252a4a", q, tag, start, count).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<BookDetailBean> getBookDetail(String id) {
        return douBanApi.getBookDetail(id, "0df993c66c0c636e29ecbb5344252a4a").compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<MovieListBean> inTheatersMovies(String city, int start, int count) {
        return douBanApi.inTheatersMovies("0df993c66c0c636e29ecbb5344252a4a", city, start, count).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<MovieListBean> comingSoonMovies(int start, int count) {
        return douBanApi.comingSoonMovies("0df993c66c0c636e29ecbb5344252a4a", start, count).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<MovieDetailBean> getMovieDetail(String id) {
        return douBanApi.getMovieDetail(id, "0df993c66c0c636e29ecbb5344252a4a").compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<String> getCelebrity(String id, int start, int count) {
        return douBanApi.getCelebrity(id, "0df993c66c0c636e29ecbb5344252a4a", start, count).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<MovieListBean> searchMovie(String q, int start, int count) {
        return douBanApi.searchMovie(q, start, count).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<List<MusicSongBean>> getSongList(Long artistId, long albumId, String folderPath, String sortOrder) {
        return RxUtils.createData(MediaLoader.getSongBeanList(artistId, albumId, folderPath, sortOrder)).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<List<MusicArtistBean>> getArtistList(String sortOrder) {
        return RxUtils.createData(MediaLoader.getArtistBeanList(sortOrder)).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<List<MusicAlbumBean>> getAlbumList(String sortOrder) {
        return RxUtils.createData(MediaLoader.getAlbumBeanList(sortOrder)).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<List<String>> getFolderList() {
        return RxUtils.createData(MediaLoader.getFolderList(App.getInstance())).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<DataBean> getData(String type, int pageIndex, int pageCount) {
        return gankApi.getData(type, pageIndex, pageCount).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Observable<DataBean> getRandomData(String type, int number) {
        return gankApi.getRandomData(type, number).compose(RxUtils.rxSchedulerHelper());
    }
}