package com.example.sibweather.model.forecast;

import com.example.sibweather.model.DayPeriod;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DetailForecast implements Serializable {

    private final @NotNull DayPeriod mDayPeriod;
    private final @NotNull Property mTemperature;
    private final @NotNull Property mPressure;
    private final @NotNull Wind mWind;
    private final @NotNull String mIconPath;

    public DetailForecast(@NotNull DayPeriod mDayPeriod,
                          @NotNull Property mTemperature,
                          @NotNull Property mPressure,
                          @NotNull Wind mWind,
                          @NotNull String mIconPath) {
        this.mDayPeriod = mDayPeriod;
        this.mTemperature = mTemperature;
        this.mPressure = mPressure;
        this.mWind = mWind;
        this.mIconPath = mIconPath;
    }

    @NotNull
    public DayPeriod getDayPeriod() {
        return mDayPeriod;
    }

    @NotNull
    public Property getTemperature() {
        return mTemperature;
    }

    @NotNull
    public Property getPressure() {
        return mPressure;
    }

    @NotNull
    public Wind getWind() {
        return mWind;
    }

    @NotNull
    public String getIconPath() {
        return mIconPath;
    }

}
