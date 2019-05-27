package com.codearms.maoqiqi.one.data.net;

import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.CommonBean;
import com.codearms.maoqiqi.one.data.bean.LoginBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerApi {

    /**
     * 登录(https://www.wanandroid.com/user/login)
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录数据
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<CommonBean<LoginBean>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 首页Banner(https://www.wanandroid.com/banner/json)
     *
     * @return Banner数据
     */
    @GET("banner/json")
    Observable<CommonBean<List<BannerBean>>> getBanner();

    /**
     * 置顶文章(https://www.wanandroid.com/article/top/json)
     *
     * @return 置顶文章数据
     */
    @GET("article/top/json")
    Observable<CommonBean<List<ArticleBean>>> getTopArticles();

    /**
     * 首页文章列表(https://www.wanandroid.com/article/list/0/json)
     *
     * @param page 页码,拼接在链接上,从0开始
     * @return 文章列表数据
     */
    @GET("article/list/{page}/json")
    Observable<CommonBean<ArticleBeans>> getArticles(@Path("page") int page);

    /**
     * 导航数据(https://www.wanandroid.com/navi/json)
     *
     * @return 导航数据
     */
    @GET("navi/json")
    Observable<CommonBean<List<NavigationBean>>> getNavigation();

    /**
     * 获取公众号列表(https://wanandroid.com/wxarticle/chapters/json)
     *
     * @return 公众号列表数据
     */
    @GET("wxarticle/chapters/json")
    Observable<CommonBean<List<ChildClassifyBean>>> getWxList();

    /**
     * 查看某个公众号历史数据(https://wanandroid.com/wxarticle/list/408/1/json)
     *
     * @param id   公众号ID,拼接在链接上,eg:405
     * @param page 公众号页码,拼接在链接上,eg:1
     * @return 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<CommonBean<ArticleBeans>> getWxNumberData(@Path("id") int id, @Path("page") int page);

    /**
     * 在某个公众号中搜索历史文章(https://wanandroid.com/wxarticle/list/405/1/json?k=Java)
     *
     * @param id   公众号ID,拼接在链接上,eg:405
     * @param page 公众号页码,拼接在链接上,eg:1
     * @param k    搜索内容,eg:Java
     * @return 指定搜索内容, 搜索当前公众号的某页的此类数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<CommonBean> getWxNumberSearchData(@Path("id") int id, @Path("page") int page, @Query("k") String k);

    /**
     * 知识体系(https://www.wanandroid.com/tree/json)
     *
     * @return 知识体系数据
     */
    @GET("tree/json")
    Observable<CommonBean<List<ParentClassifyBean>>> getKnowledge();

    /**
     * 知识体系下的文章(https://www.wanandroid.com/article/list/0/json?cid=60)
     *
     * @param page 页码,拼接在链接上,从0开始
     * @param cid  分类的id,二级目录的id
     * @return 知识体系下的文章数据
     */
    @GET("article/list/{page}/json")
    Observable<CommonBean> getArticles(@Path("page") int page, @Query("cid") int cid);

    /**
     * 项目分类(https://www.wanandroid.com/project/tree/json)
     *
     * @return 项目分类数据
     */
    @GET("project/tree/json")
    Observable<CommonBean<List<ChildClassifyBean>>> getProject();

    /**
     * 项目列表数据(https://www.wanandroid.com/project/list/1/json?cid=294)
     *
     * @param page 页码,拼接在链接上,从1开始
     * @param cid  分类的id,上面项目分类接口
     * @return 项目类别数据
     */
    @GET("project/list/{page}/json")
    Observable<CommonBean> getProjectList(@Path("page") int page, @Query("cid") int cid);
}