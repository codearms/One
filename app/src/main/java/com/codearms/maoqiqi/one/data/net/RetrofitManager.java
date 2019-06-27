package com.codearms.maoqiqi.one.data.net;

import com.codearms.maoqiqi.one.App;
import com.codearms.maoqiqi.one.BuildConfig;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String BASE_URL = "https://www.wanandroid.com/";
    private static final String BASE_NEWS_URL = "http://news-at.zhihu.com/api/4/";
    private static final int DEFAULT_TIMEOUT = 15;

    private static RetrofitManager instance;

    private OkHttpClient okHttpClient;
    private ServerApi serverApi;
    private NewsAPI newsAPI;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private void initOKHttp() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // 添加日志
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            // 设置超时
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            // 错误重连
            builder.retryOnConnectionFailure(true);
            // cookie认证
            builder.cookieJar(new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(App.getInstance())));
            okHttpClient = builder.build();
        }
    }

    public ServerApi getServerApi() {
        initOKHttp();
        if (serverApi == null) {
            serverApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ServerApi.class);
        }
        return serverApi;
    }

    public NewsAPI getNewsAPI() {
        initOKHttp();
        if (newsAPI == null) {
            newsAPI = new Retrofit.Builder()
                    .baseUrl(BASE_NEWS_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(NewsAPI.class);
        }
        return newsAPI;
    }
}