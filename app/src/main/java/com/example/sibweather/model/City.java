package com.example.sibweather.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public enum City {
    NSK("Новосибирск"),
    KRASNOYARSK("Красноярск"),
    BARNAUL("Барнаул"),
    OMSK("Омск"),
    TOMSK("Томск"),
    KEMEROVO("Кемерово"),
    IRKUTSK("Иркутск");

    private final @NotNull String val;

    City(@NotNull String val) {
        this.val = val;
    }

    @NotNull
    public String getVal() {
        return val;
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

    @NotNull
    public static String[] getAllCitiesNames() {
        final City[] cities = City.values();

        final String[] names = new String[cities.length];

        for (int i = 0; i < cities.length; i++) {
            names[i] = cities[i].getVal();
        }
        return names;
    }
    //--------------------------------------------------------------------------------------------

}

