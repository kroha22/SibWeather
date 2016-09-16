package com.example.weatherparser.mvp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olga
 * on 15.09.2016.
 */
public enum CityNames {
    NOVOSIBIRSK("Новосибирск"),
    KRASNOYARSK("Красноярск"),
    BARNAUL("Барнаул"),
    OMSK("Омск"),
    TOMSK("Томск"),
    KEMEROVO("Кемерово"),
    IRKUTSK("Иркутск");

    private String mName;

    CityNames(String string) {
        mName = string;
    }

    public static List<CityNames> getCities() {
        return new ArrayList<>(Arrays.asList(NOVOSIBIRSK, KRASNOYARSK, BARNAUL,
                OMSK, TOMSK, KEMEROVO, IRKUTSK));
    }

    public String getName() {
        return mName;
    }
}

