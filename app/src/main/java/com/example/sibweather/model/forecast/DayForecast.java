package com.example.sibweather.model.forecast;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

public class DayForecast implements Serializable {

    private final @NotNull LocalDate mDate;
    private final @NotNull List<DetailForecast> mDetailForecasts;

    public DayForecast(@NotNull LocalDate mDate, @NotNull List<DetailForecast> mDetailForecasts) {
        this.mDate = mDate;
        this.mDetailForecasts = mDetailForecasts;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public List<DetailForecast> getDetail() {
        return mDetailForecasts;
    }
}
