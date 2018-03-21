package com.example.sibweather.model.forecast;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class DayForecast {

    private final @NotNull LocalDate date;
    private final @NotNull List<DetailForecast> detailForecasts;

    public DayForecast(@NotNull LocalDate date, @NotNull List<DetailForecast> detailForecasts) {
        this.date = date;
        this.detailForecasts = detailForecasts;
    }

    @NotNull
    public LocalDate getDate() {
        return date;
    }

    @NotNull
    public List<DetailForecast> getDetail() {
        return detailForecasts;
    }

    public Property getTemperature(){
        return detailForecasts.get(2).getTemperature();
    }

    public String getIconPath(){
        return detailForecasts.get(2).getIconPath();
    }


}
