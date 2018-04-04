package com.daotangbill.exlib.sample;


import java.io.InputStream;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BILL on 2016/12/27.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */
class NetClient {
    /**
     * 获取Retrofit适配器。
     *
     * @return 网络适配器
     */
    static Retrofit newRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
//                .baseUrl("http://192.168.0.204:8100/")
                .client(getClient())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static Retrofit newRxRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
//                .baseUrl("http://192.168.0.204:8100/")
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取 OkHttpClient
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getClient() {
        OkHttpClient client = null;
        InputStream bksFile = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
//                .cookieJar(new CookieManger(APP.getInstance()))
                .connectionPool(new ConnectionPool(4, 10, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
//                .addInterceptor(new AddCookiesInterceptor(AppManager.getInstance()))
//                .addInterceptor(new ReceivedCookiesInterceptor(AppManager.getInstance()))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .addInterceptor(new LoggerInterceptor("ok_http"))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1));
        client = builder.build();
        return client;
    }
}