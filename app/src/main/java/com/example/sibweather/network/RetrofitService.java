package com.example.sibweather.network;

import android.content.Context;

import com.example.sibweather.R;
import com.example.sibweather.model.City;
import com.example.sibweather.model.forecast.WeatherResponse;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public class RetrofitService {
    //--------------------------------------------------------------------------------------------
    private interface WeatherApi {
        @GET(WeatherUrls.Forecast.GET_FORECAST)
        Observable<WeatherResponse> getWeather(@Query("city") String city);
    }
    //--------------------------------------------------------------------------------------------
    private final static class WeatherUrls {
        //-----------------------------------------------------------------------------------------------
        private static class Forecast {
            static final String BASE = API + "/forecasts";
            static final String GET_FORECAST = BASE + "/forecast";
        }
        //-----------------------------------------------------------------------------------------------
        private static final String API = "/api/v1";

        private static String getApiBaseUrl(Context context) {
            return context.getString(R.string.base_api_url);
        }
    }
    //--------------------------------------------------------------------------------------------
    private static RetrofitService sInstance;

    public static RetrofitService getInstance(Context context) {
        if (sInstance == null) {
            synchronized (RetrofitService.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitService(context);
                }
            }
        }

        return sInstance;
    }
    //--------------------------------------------------------------------------------------------

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

    public Observable<WeatherResponse> getWeather(@NotNull City city) {
        return createApiService(WeatherApi.class)
                .getWeather(city.name().toLowerCase());
    }

    @NotNull
    private  <S> S createApiService(@NotNull Class<S> apiClass) {
        return mRetrofit.create(apiClass);
    }
}
