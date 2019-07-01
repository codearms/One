package com.codearms.maoqiqi.one.data.net;

import com.codearms.maoqiqi.one.data.bean.BookListBean;
import com.codearms.maoqiqi.one.data.bean.MovieDetailBean;
import com.codearms.maoqiqi.one.data.bean.MovieListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DouBanApi {

    /**
     * 搜索图书(https://api.douban.com/v2/book/search)
     *
     * @param q     查询关键字
     * @param tag   查询的tag
     * @param start 取结果的offset 默认为0
     * @param count 取结果的条数 默认为20，最大为100
     */
    @GET("book/search")
    Observable<BookListBean> getBook(@Query("apikey") String apiKey, @Query("q") String q, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    /**
     * 获取图书信息(https://api.douban.com/v2/book/:id)
     *
     * @param id
     */
    @GET("book/{id}")
    Observable<BookListBean.BookBean> getBookDetail(@Path("id") String id);

    /**
     * 正在热映
     */
    @GET("movie/in_theaters")
    Observable<MovieListBean> inTheatersMovies(@Query("apikey") String apiKey, @Query("city") String city, @Query("start") int start, @Query("count") int count);

    /**
     * 即将上映
     */
    @GET("movie/coming_soon")
    Observable<MovieListBean> comingSoonMovies(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情(https://api.douban.com/v2/movie/subject/26942674)
     *
     * @param id 电影豆瓣ID
     */
    @GET("movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id, @Query("apikey") String apiKey);

    /**
     * 影人条目信息
     *
     * @param id
     */
    @GET("movie/celebrity/{id}")
    Observable<String> getCelebrity(@Path("id") String id);

    /**
     * 搜索电影
     */
    @GET("movie/search")
    Observable<MovieListBean> searchMovie(@Query("q") String q, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);
}