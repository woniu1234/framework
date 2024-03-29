package com.common.net;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.common.net.interceptor.ResponseInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApi {

    protected Retrofit retrofit;

    public abstract String getBaseUrl();

    public abstract ObservableTransformer getErrorFormer();

    protected BaseApi() {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    protected BaseApi(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public int getTimeOut() {
        return 15;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(getTimeOut(), TimeUnit.SECONDS)
                .readTimeout(getTimeOut(), TimeUnit.SECONDS)
                .writeTimeout(getTimeOut(), TimeUnit.SECONDS);
        //请求相应
        okHttpClient.addInterceptor(new ResponseInterceptor());
        //自定义Interceptor
        if (!getInterceptorList().isEmpty()) {
            for (Interceptor interceptor : getInterceptorList()) {
                okHttpClient.addInterceptor(interceptor);
            }
        }
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    public List<Interceptor> getInterceptorList() {
        return new ArrayList<>();
    }

    public boolean isDebug() {
        return true;
    }

    private void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //BODY打印信息,NONE不打印信息
        logging.setLevel(isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
    }

    /**
     * 封装线程管理和订阅的过程，在主线程相应
     */
    protected void apiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getErrorFormer())
                .subscribe(observer);
    }

    /**
     * 封装线程管理和订阅的过程,在子线程相应
     */
    protected void apiSubscribeTx(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(getErrorFormer())
                .subscribe(observer);
    }
}
