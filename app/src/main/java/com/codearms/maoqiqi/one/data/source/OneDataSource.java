package com.codearms.maoqiqi.one.data.source;

import com.codearms.maoqiqi.one.data.bean.ArticleBean;
import com.codearms.maoqiqi.one.data.bean.ArticleBeans;
import com.codearms.maoqiqi.one.data.bean.BannerBean;
import com.codearms.maoqiqi.one.data.bean.ChildClassifyBean;
import com.codearms.maoqiqi.one.data.bean.HotKeyBean;
import com.codearms.maoqiqi.one.data.bean.NavigationBean;
import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;
import com.codearms.maoqiqi.one.data.bean.ParentClassifyBean;
import com.codearms.maoqiqi.one.data.bean.UsefulSitesBean;
import com.codearms.maoqiqi.one.data.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;

public interface OneDataSource {

    /**
     * 首页Banner(https://www.wanandroid.com/banner/json)
     *
     * @return Banner数据
     */
    Observable<List<BannerBean>> getBanner();

    /**
     * 常用网站(https://www.wanandroid.com/friend/json)
     *
     * @return 常用网站数据
     */
    Observable<List<UsefulSitesBean>> getUsefulSites();

    /**
     * 热词(https://www.wanandroid.com/hotkey/json)
     *
     * @return 热词数据
     */
    Observable<List<HotKeyBean>> getHotKey();

    /**
     * 置顶文章(https://www.wanandroid.com/article/top/json)
     *
     * @return 置顶文章数据
     */
    Observable<List<ArticleBean>> getTopArticles();

    /**
     * 首页文章列表(https://www.wanandroid.com/article/list/0/json)
     *
     * @param page 页码,拼接在链接上,从0开始
     * @return 文章列表数据
     */
    Observable<ArticleBeans> getArticles(int page);

    /**
     * 导航数据(https://www.wanandroid.com/navi/json)
     *
     * @return 导航数据
     */
    Observable<List<NavigationBean>> getNavigation();

    /**
     * 获取公众号列表(https://wanandroid.com/wxarticle/chapters/json)
     *
     * @return 公众号列表数据
     */
    Observable<List<ChildClassifyBean>> getWxList();

    /**
     * 查看某个公众号历史数据(https://wanandroid.com/wxarticle/list/408/1/json)
     *
     * @param id   公众号ID,拼接在链接上,eg:405
     * @param page 公众号页码,拼接在链接上,eg:1
     * @return 查看某个公众号历史数据
     */
    Observable<ArticleBeans> getWxArticles(int id, int page);

    /**
     * 在某个公众号中搜索历史文章(https://wanandroid.com/wxarticle/list/405/1/json?k=Java)
     *
     * @param id   公众号ID,拼接在链接上,eg:405
     * @param page 公众号页码,拼接在链接上,eg:1
     * @param k    搜索内容,eg:Java
     * @return 指定搜索内容, 搜索当前公众号的某页的此类数据
     */
    Observable<Object> getWxSearchArticles(int id, int page, String k);

    /**
     * 知识体系(https://www.wanandroid.com/tree/json)
     *
     * @return 知识体系数据
     */
    Observable<List<ParentClassifyBean>> getKnowledge();

    /**
     * 知识体系下的文章(https://www.wanandroid.com/article/list/0/json?cid=60)
     *
     * @param page 页码,拼接在链接上,从0开始
     * @param cid  分类的id,二级目录的id
     * @return 知识体系下的文章数据
     */
    Observable<ArticleBeans> getKnowledgeArticles(int page, int cid);

    /**
     * 项目分类(https://www.wanandroid.com/project/tree/json)
     *
     * @return 项目分类数据
     */
    Observable<List<ChildClassifyBean>> getProject();

    /**
     * 项目列表数据(https://www.wanandroid.com/project/list/1/json?cid=294)
     *
     * @param page 页码,拼接在链接上,从1开始
     * @param cid  分类的id,上面项目分类接口
     * @return 项目类别数据
     */
    Observable<ArticleBeans> getProjectArticles(int page, int cid);

    /**
     * 登录(https://www.wanandroid.com/user/login)
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录
     */
    Observable<UserBean> login(String username, String password);

    /**
     * 注册(https://www.wanandroid.com/user/register)
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return 注册
     */
    Observable<UserBean> register(String username, String password, String repassword);

    /**
     * 退出(https://www.wanandroid.com/user/logout/json)
     *
     * @return 退出
     */
    Observable<Object> logout();

    /**
     * 收藏文章列表(https://www.wanandroid.com/lg/collect/list/0/json)
     *
     * @param page 页码,拼接在链接上,从0开始
     * @return 收藏文章列表数据
     */
    Observable<ArticleBeans> getCollect(int page);

    /**
     * 收藏站内文章(https://www.wanandroid.com/lg/collect/1165/json)
     *
     * @param id 文章id,拼接在链接上
     * @return 收藏站内文章
     */
    Observable<Object> collect(int id);

    /**
     * 收藏站外文章(https://www.wanandroid.com/lg/collect/add/json)
     *
     * @param title  标题
     * @param author 作者
     * @param link   链接
     * @return 收藏站外文章
     */
    Observable<ArticleBean> collect(String title, String author, String link);

    /**
     * 取消收藏[文章列表](https://www.wanandroid.com/lg/uncollect_originId/2333/json)
     *
     * @param id 文章id,拼接在链接上
     * @return 取消收藏
     */
    Observable<Object> unCollect(int id);

    /**
     * 取消收藏[我的收藏页面](https://www.wanandroid.com/lg/uncollect/2805/json)
     *
     * @param id       文章id,拼接在链接上
     * @param originId 源id
     * @return 取消收藏
     */
    Observable<Object> unCollect(int id, int originId);

    /**
     * 收藏网站列表(https://www.wanandroid.com/lg/collect/usertools/json)
     *
     * @return 收藏网站列表数据
     */
    Observable<Object> getCollectUrl();

    /**
     * 收藏网址(https://www.wanandroid.com/lg/collect/addtool/json)
     *
     * @param name name
     * @param link link
     * @return 收藏网址
     */
    Observable<Object> collectUrl(String name, String link);

    /**
     * 编辑收藏网站(https://www.wanandroid.com/lg/collect/updatetool/json)
     *
     * @param id   id
     * @param name name
     * @param link link
     * @return 编辑收藏网站
     */
    Observable<Object> collectUrl(int id, String name, String link);

    /**
     * 删除收藏网站(https://www.wanandroid.com/lg/collect/deletetool/json)
     *
     * @param id id
     * @return 删除收藏网站
     */
    Observable<Object> unCollectUrl(int id);

    /**
     * 搜索(https://www.wanandroid.com/article/query/0/json)
     *
     * @param page 页码
     * @param k    搜索关键词
     * @return 搜索数据
     */
    Observable<ArticleBeans> query(int page, String k);

    // ============================================================================================

    Observable<NewsBean> getLatestNews();

    Observable<NewsDetailBean> getNewsDetail(int id);
}