package com.example.weatherparser.network;

import com.example.weatherparser.mvp.model.forecast.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Date: 21.06.2016
 * Time: 00:31
 *
 * @author Jeksor
 */
public interface WeatherApi {

    @GET(WeatherUrls.Forecast.GET_FORECAST)
    Observable<WeatherResponse> getForecast(@Query("city") String city);

}
