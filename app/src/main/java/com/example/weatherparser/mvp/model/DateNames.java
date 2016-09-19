package com.example.weatherparser.mvp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 08.09.16
 * Time: 22:21
 *
 * @author Olga
 */
public enum DateNames {

    MORNING("утро"),
    DAY("день"),
    EVENING("вечер"),
    NIGHT("ночь");

    private String mName;

    DateNames(String string) {
        mName = string;
    }

    public static List<DateNames> getTime() {
        return new ArrayList<>(Arrays.asList(NIGHT, MORNING, DAY, EVENING));
    }

    public String getName() {
        return mName;
    }
}
