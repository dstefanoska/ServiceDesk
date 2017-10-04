package com.daka.sdk.services;

import com.daka.sdk.Config;
import com.daka.sdk.auth.TokenAuthenticationProvider;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Dana on 22-Sep-17.
 */

public class Daka {

    static Config config;

    public static void init(Config config) {
        Daka.config = config;
    }

    public static Gson gson() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson;
    }

    private static Retrofit.Builder retrofit() {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .client(authClient().build());

        //retrofit.addCallAdapterFactory(new RxThreadCallAdapater(Schedulers.io(), AndroidSchedulers.mainThread()));
        retrofit.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return retrofit;
    }

    private static OkHttpClient.Builder authClient() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        if (config.isLogEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.d(message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }

        client.interceptors().add(TokenAuthenticationProvider.getInstance());
        return client;
    }

    public static ApiService getApiService() {
        Retrofit retrofit = retrofit()
                .baseUrl(config.getUrl())
                .build();
        return retrofit.create(ApiService.class);
    }

    public static MultipartBody.Part createFileBody(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }
}
