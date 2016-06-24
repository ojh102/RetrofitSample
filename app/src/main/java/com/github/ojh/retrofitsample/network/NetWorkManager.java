package com.github.ojh.retrofitsample.network;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JaeHwan Oh on 2016-06-24.
 */
public class NetWorkManager {
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    private static final String SERVER = "https://apis.daum.net/";
    Retrofit client;

    // singleton holder pattern : thread safe, lazy class initialization, memory saving.
    public static class InstanceHolder {
        public static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    public static NetWorkManager getInstance() {
        return InstanceHolder.INSTANCE;
    }


    private NetWorkManager() {
        //Retrofit Enviroment setting.

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //set logging level

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //set time out
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging) //set logging
                .build();

        client = new Retrofit.Builder()
                .baseUrl(SERVER) // where your server lives
                .client(okHttpClient) // what your http environment is
                .addConverterFactory(GsonConverterFactory.create()) // with what data format you want to transmit, in my case JSON
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // for use RxAndroid
                .build();
    }

    //API Return
    public <T> T getApi(Class<T> serviceClass) {
        // connecting my API and my Retrofit environment and return.
        // then I'm able to call my API and make use of it
        return client.create(serviceClass);
    }
}
