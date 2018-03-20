package com.example.sibweather.model.forecast;

import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.core.deps.guava.collect.Lists;

import com.example.sibweather.model.DayPeriod;
import com.example.sibweather.utils.TimeUtils;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Date: 08.09.16
 * Time: 20:19
 *
 * @author Olga
 */
public class WeatherResponse {

    @SerializedName("metadata")
    private Metadata mMetadata;

    @SerializedName("forecasts")
    private List<Forecast> mForecasts;

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata metadata) {
        mMetadata = metadata;
    }

    public List<Forecast> getForecasts() {
        return mForecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        mForecasts = forecasts;
    }

    public List<DayForecast> getDayForecasts() {
        return mForecasts != null ?
                Lists.newArrayList(Iterables.transform(mForecasts, WeatherResponse::getDayForecast)) :
                Collections.emptyList();
    }

    private static DayForecast getDayForecast(@NotNull Forecast forecast) {
        return new DayForecast(
                TimeUtils.getLocalDate(forecast.getDate()),
                Lists.newArrayList(Iterables.transform(forecast.getHours(), WeatherResponse::getDetailForecast))
        );
    }

    private static DetailForecast getDetailForecast(@NotNull Hour hour) {
        return new DetailForecast(
                DayPeriod.getByHours(hour.getHour()),
                hour.getTemperature(),
                hour.getPressure(),
                hour.getWind(),
                hour.getIcon_path()
        );
    }
}
