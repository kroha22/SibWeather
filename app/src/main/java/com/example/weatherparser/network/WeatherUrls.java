package com.example.weatherparser.network;

import android.content.Context;

import com.example.weatherparser.R;

/**
 * Date: 21.06.2016
 * Time: 00:32
 *
 * @author Jeksor
 */
public final class WeatherUrls {
    static final String API = "/api/v1";

    public static class Forecast {
        static final String BASE = API + "/forecasts";

        public static final String GET_FORECAST = BASE + "/forecast";
    }

    public static String getApiBaseUrl(Context context)
    {
        return context.getString(R.string.base_api_url);
    }
}
