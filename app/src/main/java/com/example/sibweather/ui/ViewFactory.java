package com.example.sibweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sibweather.R;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.utils.TimeUtils;

import org.jetbrains.annotations.NotNull;

import static com.example.sibweather.ui.ImageUtils.loadSmallIconInto;

/**
 * Created by Olga
 * on 30.09.2017.
 */
class ViewFactory {

    static View getProgressView(@NotNull Context context) {
        return LayoutInflater.from(context).inflate(R.layout.progress_view, null, false);
    }

    @NotNull
    static View getDayWeatherView(@NotNull Context context, @NotNull DayForecast forecast) {
        final RelativeLayout mainView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.weather_day, null, false);

        final TextView date = (TextView) mainView.findViewById(R.id.today_weather_date);
        final TextView day = (TextView) mainView.findViewById(R.id.today_weather_day_of_week);
        final ImageView weatherImg = (ImageView) mainView.findViewById(R.id.today_weather_pic);
        final TextView temp = (TextView) mainView.findViewById(R.id.today_weather_temp);

        initWeatherView(date, day, temp, forecast);
        loadSmallIconInto(weatherImg, forecast.getIconPath());

        return mainView;
    }

    private static void initWeatherView(@NotNull TextView date,
                                        @NotNull TextView day,
                                        @NotNull TextView temp,
                                        @NotNull DayForecast forecast) {
        day.setText(TimeUtils.getDayOfWeekShort(forecast.getDate()));
        date.setText(TimeUtils.getShortDate(forecast.getDate()));

        final int tempInt = forecast.getTemperature().getAvg();
        final String tempStr = tempInt > 0 ? "+" + tempInt : Integer.toString(tempInt);
        temp.setText(tempStr);
    }
}
