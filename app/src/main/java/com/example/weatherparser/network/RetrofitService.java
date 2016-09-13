package com.example.weatherparser.network;

import android.content.Context;
import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date: 21.06.2016
 * Time: 00:40
 *
 * @author Jeksor
 */
public class RetrofitService {

    private static RetrofitService sInstance;

    public static RetrofitService getInstance(Context context)
    {
        if (sInstance == null)
        {
            synchronized (RetrofitService.class)
            {
                if (sInstance == null)
                {
                    sInstance = new RetrofitService(context);
                }
            }
        }

        return sInstance;
    }

    private Retrofit mRetrofit;

    private RetrofitService(Context context) {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(WeatherUrls.getApiBaseUrl(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(logInterceptor).build())
                .build();
    }

    @NonNull
    public <S> S createApiService(@NonNull final Class<S> apiClass)
    {
        return mRetrofit.create(apiClass);
    }
}
