package com.codearms.maoqiqi.one.data.net;

import com.codearms.maoqiqi.one.data.bean.DataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-09 14:40
 */
public interface GankApi {

    @GET("data/{type}/{pageCount}/{pageIndex}")
    Observable<DataBean> getData(@Path("type") String type, @Path("pageIndex") int pageIndex, @Path("pageCount") int pageCount);

    @GET("random/data/{type}/{number}")
    Observable<DataBean> getRandomData(@Path("type") String type, @Path("number") int number);
}