package com.example.sibweather.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 08.09.16
 * Time: 22:21
 *
 * @author Olga
 */
public enum DayPeriod {

    MORNING("утро", 0),
    DAY("день", 1),
    EVENING("вечер", 2),
    NIGHT("ночь", 3);

    private final int mDayPeriod;
    private final @NotNull String mVal;

    DayPeriod(String val, int dayPath) {
        mVal = val;
        mDayPeriod = dayPath;
    }

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

    public static List<DayPeriod> getTime() {
        return new ArrayList<>(Arrays.asList(NIGHT, MORNING, DAY, EVENING));
    }

    public String getVal() {
        return mVal;
    }

    public boolean include(int hour){
        final int dayPath = hour / 6;
        return dayPath == mDayPeriod;
    }
}
