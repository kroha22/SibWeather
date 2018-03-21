package com.example.sibweather.model.forecast;

import com.example.sibweather.model.DayPeriod;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class DetailForecast {

    private final @NotNull DayPeriod dayPeriod;
    private final @NotNull Property temperature;
    private final @NotNull Property pressure;
    private final @NotNull Wind wind;
    private final @NotNull String iconPath;
    private final @NotNull String bigIconPath;

    public DetailForecast(@NotNull DayPeriod dayPeriod,
                          @NotNull Property temperature,
                          @NotNull Property pressure,
                          @NotNull Wind wind,
                          @NotNull String iconPath,
                          @NotNull String bigIconPath) {
        this.dayPeriod = dayPeriod;
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
        this.iconPath = iconPath;
        this.bigIconPath = bigIconPath;
    }

    @NotNull
    public DayPeriod getDayPeriod() {
        return dayPeriod;
    }

    @NotNull
    public Property getTemperature() {
        return temperature;
    }

    @NotNull
    public Property getPressure() {
        return pressure;
    }

    @NotNull
    public Wind getWind() {
        return wind;
    }

    @NotNull
    public String getIconPath() {
        return iconPath;
    }

    @NotNull
    public String getBigIconPath() {
        return bigIconPath;
    }

}
