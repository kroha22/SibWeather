package com.example.sibweather.utils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Instant;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Olga
 * on 30.09.2017.
 */

public class TimeUtils {

    public static String getDayOfWeek(@NotNull LocalDate date){
        return date.dayOfWeek().getAsShortText();
    }

    public static String getDate(@NotNull LocalDate date){
        return date.toString("%td %tB", Locale.ROOT);
    }

    public static Instant getDate(@NotNull String date) {
        try {
            return new Instant(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2016-12-31"));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format " + date);
        }
    }

    public static LocalDate getLocalDate(@NotNull String date) {
        try {
            return new LocalDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse("2016-12-31"));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format " + date);
        }
    }
}
