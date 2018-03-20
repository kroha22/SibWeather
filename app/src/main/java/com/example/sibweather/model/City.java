package com.example.sibweather.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Olga
 * on 15.09.2016.
 */
public enum City {
    NSK("Новосибирск"),
    KRASNOYARSK("Красноярск"),
    BARNAUL("Барнаул"),
    OMSK("Омск"),
    TOMSK("Томск"),
    KEMEROVO("Кемерово"),
    IRKUTSK("Иркутск");

    private final @NotNull String mVal;

    City(@NotNull String val) {
        mVal = val;
    }

    public String getVal() {
        return mVal;
    }

    //--------------------------------------------------------------------------------------------
    @NotNull
    public static City getCity(@NotNull String name) {
        for (City city : City.values()) {
            if (name.equals(city.getVal())) {
                return city;
            }
        }
        throw new IllegalArgumentException("Unknown city \"" + name + "\"");
    }
    //--------------------------------------------------------------------------------------------

}

