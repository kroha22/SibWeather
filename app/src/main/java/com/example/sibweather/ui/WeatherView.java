package com.example.sibweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sibweather.R;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.model.forecast.DetailForecast;
import com.example.sibweather.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 08.09.16
 * Time: 19:30
 *
 * @author Olga
 */
public class WeatherView {

    @NotNull
    public static View getDayWeatherView(@NotNull Context context, @NotNull DayForecast forecast) {
        final LinearLayout mainView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.weather_day, null, false);

        final TextView date = (TextView) mainView.findViewById(R.id.day_weather_date);
        final TextView day = (TextView) mainView.findViewById(R.id.day_weather_day_of_week);
        final ImageView weatherImg = (ImageView) mainView.findViewById(R.id.day_weather_pic);
        final TextView temp = (TextView) mainView.findViewById(R.id.day_weather_temp);

        initWeatherView(context, date, day, weatherImg, temp, forecast);

        return mainView;
    }

    @NotNull
    public static View getTodayWeatherView(@NotNull Context context, @NotNull DayForecast forecast) {
        final LinearLayout mainView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.weather_today, null, false);

        final TextView date = (TextView) mainView.findViewById(R.id.today_weather_date);
        final TextView day = (TextView) mainView.findViewById(R.id.today_weather_day_of_week);
        final ImageView weatherImg = (ImageView) mainView.findViewById(R.id.today_weather_pic);
        final TextView temp = (TextView) mainView.findViewById(R.id.today_weather_temp);

        initWeatherView(context, date, day, weatherImg, temp, forecast);

        return mainView;
    }

    private static void initWeatherView(@NotNull Context context,
                                        @NotNull TextView date,
                                        @NotNull TextView day,
                                        @NotNull ImageView weatherImg,
                                        @NotNull TextView temp,
                                        @NotNull DayForecast forecast) {
        day.setText(TimeUtils.getDayOfWeek(forecast.getDate()));
        date.setText(TimeUtils.getDate(forecast.getDate()));

        final DetailForecast dayForecast = forecast.getDetail().get(2);
        final int tempInt = dayForecast.getTemperature().getAvg();
        final String tempStr = tempInt > 0 ? "+" + tempInt : Integer.toString(tempInt);
        temp.setText(tempStr);

        Picasso.with(context)
                .load("http:" + dayForecast.getIconPath())
                .resize(40, 30)
                .into(weatherImg);
    }

}

