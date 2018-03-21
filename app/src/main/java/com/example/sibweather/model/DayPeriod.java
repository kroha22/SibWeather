package com.example.sibweather.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public enum DayPeriod {

    MORNING("Утро", 0),
    DAY("День", 1),
    EVENING("Вечер", 2),
    NIGHT("Ночь", 3);

    //-------------------------------------------------------------------------------------------------
    public static DayPeriod getByHours(int hours) {
        final int currentDayPath = hours / 6;

        switch (currentDayPath) {
            case 0:
                return MORNING;

            case 1:
                return DAY;

            case 2:
                return EVENING;

            case 3:
                return NIGHT;

            default:
                throw new AssertionError("Error hours " + hours + ": hours must be > 0 and < 24");
        }
    }
    //-------------------------------------------------------------------------------------------------

    private final int dayPeriod;
    private final @NotNull String val;

    DayPeriod(@NotNull String val, int dayPath) {
        this.val = val;
        this.dayPeriod = dayPath;
    }

    @NotNull
    public String getVal() {
        return val;
    }

    public boolean include(int hour) {
        final int dayPath = hour / 6;
        return dayPath == dayPeriod;
    }
}
