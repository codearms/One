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
     * @param apiKey apiKey
     * @param q      查询关键字
     * @param tag    查询的tag
     * @param start  取结果的offset 默认为0
     * @param count  取结果的条数 默认为20，最大为100
     * @return 图书数据
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
     * 获取正在热映的电影(https://api.douban.com/v2/movie/in\_theaters?city=广州&start=0&count=10)
     *
     * @param apiKey apiKey
     * @param city   查询城市
     * @param start  从第几条开始
     * @param count  一页条数
     * @return 热映数据
     */
    @GET("movie/in_theaters")
    Observable<MovieListBean> inTheatersMovies(@Query("apikey") String apiKey, @Query("city") String city, @Query("start") int start, @Query("count") int count);

    /**
     * 获取即将上映影片(https://api.douban.com/v2/movie/coming_soon?start=1&count=1)
     *
     * @param apiKey apiKey
     * @param start  从第几条开始
     * @param count  一页条数
     * @return 即将上映数据
     */
    @GET("movie/coming_soon")
    Observable<MovieListBean> comingSoonMovies(@Query("apikey") String apiKey, @Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情(https://api.douban.com/v2/movie/subject/26942674)
     *
     * @param id     电影豆瓣ID
     * @param apiKey apiKey
     * @return 电影详情
     */
    @GET("movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id, @Query("apikey") String apiKey);

    /**
     * 获取影人条目信息(https://api.douban.com/v2/movie/celebrity/1044707?apikey=xxxx)
     *
     * @param id     豆瓣ID
     * @param apiKey apiKey
     * @param start  从第几条开始
     * @param count  一页条数
     * @return 影人条目信息
     */
    @GET("movie/celebrity/{id}")
    Observable<String> getCelebrity(@Path("id") String id, @Query("apikey") String apiKey, @Query("start") int start, @Query("count") int count);

    /**
     * 搜索电影(https://api.douban.com/v2/movie/search?q=后天)
     *
     * @param q     查询关键词
     * @param start 从第几条开始
     * @param count 一页条数
     * @return 搜索数据
     */
    @GET("movie/search")
    Observable<MovieListBean> searchMovie(@Query("q") String q, @Query("start") int start, @Query("count") int count);
}