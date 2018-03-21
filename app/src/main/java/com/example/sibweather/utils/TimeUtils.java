package com.example.sibweather.utils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public class TimeUtils {
    //---------------------------------------------------------------------------------------------
    private final static String ShortDatePattern = "dd.MM";
    private final static String DateTimePattern = "yyyy-MM-dd'T'HH:mm:ssZ";
    //---------------------------------------------------------------------------------------------

    public static long getMillis(@NotNull LocalDate date) {
        return date.toDateTime(LocalTime.MIDNIGHT, DateTimeZone.getDefault()).toInstant().getMillis();
    }

    public static LocalDate fromMillis(long date) {
        return new Instant(date).toDateTime(DateTimeZone.getDefault()).toLocalDate();
    }

    public static String getDayOfWeekShort(@NotNull LocalDate date) {
        return date.dayOfWeek().getAsShortText(new Locale("ru", "RU"));
    }

    public static String getDayOfWeek(@NotNull LocalDate date) {
        return date.dayOfWeek().getAsText(new Locale("ru", "RU"));
    }

    public static String getShortDate(@NotNull LocalDate date) {
        return date.toString(ShortDatePattern, Locale.ROOT);
    }

    public static LocalDate getLocalDate(@NotNull String date) {
        try {
            return new LocalDate(new SimpleDateFormat(DateTimePattern, Locale.ROOT).parse(date));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format " + date);
        }
    }
}
