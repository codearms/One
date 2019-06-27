package com.codearms.maoqiqi.one.data.net;

import com.codearms.maoqiqi.one.data.bean.NewsBean;
import com.codearms.maoqiqi.one.data.bean.NewsDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsAPI {

    /**
     * 得到最新的新闻
     *
     * @return 新闻数据
     */
    @GET("stories/latest")
    Observable<NewsBean> getLatestNews();

    /**
     * 得到指定时间新闻
     *
     * @param date 时间
     * @return 新闻数据
     */
    @GET("stories/before/{date}")
    Observable<NewsBean> getBeforeNews(@Path("date") String date);

    /**
     * 得到新闻详情
     *
     * @param id 新闻id
     * @return 新闻详情数据
     */
    @GET("story/{id}")
    Observable<NewsDetailBean> getNewsDetail(@Path("id") int id);
}