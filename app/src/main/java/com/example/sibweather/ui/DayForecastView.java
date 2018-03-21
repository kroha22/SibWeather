package com.example.sibweather.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sibweather.R;
import com.example.sibweather.model.forecast.DetailForecast;
import com.example.sibweather.model.forecast.Property;
import com.example.sibweather.model.forecast.Wind;
import com.example.sibweather.utils.Action;
import com.example.sibweather.utils.TimeUtils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import static com.example.sibweather.ui.ImageUtils.loadBigIconInto;
import static com.example.sibweather.ui.ImageUtils.loadSmallIconInto;

/**
 * Created by Olga
 * on 30.09.2017.
 */
final class DayForecastView {

    //--------------------------------------------------------------------------------------------
    private static class HourForecastView {
        private final @NotNull RelativeLayout mainView;
        private final @NotNull ImageView weatherImg;
        private final @NotNull TextView temp;

        private HourForecastView(@NotNull RelativeLayout mainView, @NotNull ImageView weatherImg, @NotNull TextView temp) {
            this.mainView = mainView;
            this.weatherImg = weatherImg;
            this.temp = temp;
        }

        private void init(@NotNull DetailForecast forecast, @NotNull Action onClickListener) {

            final int tempInt = forecast.getTemperature().getAvg();
            final String tempStr = tempInt > 0 ? "+" + tempInt : Integer.toString(tempInt);
            temp.setText(tempStr);

            loadSmallIconInto(weatherImg, forecast.getIconPath());

            mainView.setOnClickListener(v -> onClickListener.execute());
        }

        private void setSelected(Context context, boolean isSelected) {
            final Drawable background = ContextCompat.getDrawable(context, isSelected ? R.drawable.green_bubble : R.drawable.blue_bubble);

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                mainView.setBackgroundDrawable(background);
            } else {
                mainView.setBackground(background);
            }
        }
    }

    //--------------------------------------------------------------------------------------------
    private static class SelectedHourForecastView {

        private final @NotNull TextView temp;
        private final @NotNull TextView wind;
        private final @NotNull TextView press;
        private final @NotNull ImageView weatherImg;

        private SelectedHourForecastView(@NotNull TextView temp,
                                         @NotNull TextView wind,
                                         @NotNull TextView press,
                                         @NotNull ImageView weatherImg) {
            this.temp = temp;
            this.wind = wind;
            this.press = press;
            this.weatherImg = weatherImg;
        }

        private void init(@NotNull DetailForecast forecast) {
            loadBigIconInto(weatherImg, forecast.getBigIconPath());
            temp.setText(getTempDeltaStr(forecast.getTemperature()));
            wind.setText(getWindStr(forecast.getWind()));
            press.setText(getPressureStr(forecast.getPressure()));
        }

        @NotNull
        private String getTempDeltaStr(@NotNull Property temp) {
            final int min = temp.getMin();
            String temperature = min > 0 ? "+" + min : Integer.toString(min);

            final int max = temp.getMax();
            temperature = temperature + " .. " + (max > 0 ? "+" + max : Integer.toString(max));

            return temperature;
        }

        @NotNull
        private String getPressureStr(@NotNull Property pressure) {
            return pressure.getAvg() + " мм";
        }

        @NotNull
        private String getWindStr(@NotNull Wind wind) {
            return wind.getSpeed().getMin() + "-" + wind.getSpeed().getMax() + " " + wind.getDirection().getTitleShort();
        }
    }

    //--------------------------------------------------------------------------------------------

    private final @NotNull Context context;

    private final @NotNull TextView city;
    private final @NotNull TextView dayOfWeek;
    private final @NotNull TextView date;

    private final @NotNull SelectedHourForecastView selectedHourForecast;
    private final @NotNull List<HourForecastView> hourForecastsViews;

    private RelativeLayout mainView;

    DayForecastView(@NotNull Context context) {
        this.context = context;

        mainView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.day_forecast, null, false);
        city = (TextView) mainView.findViewById(R.id.day_forecast_city);
        date = (TextView) mainView.findViewById(R.id.day_forecast_date);
        dayOfWeek = (TextView) mainView.findViewById(R.id.day_forecast_day_of_week);

        selectedHourForecast = new SelectedHourForecastView(
                (TextView) mainView.findViewById(R.id.day_forecast_temp),
                (TextView) mainView.findViewById(R.id.day_forecast_wind),
                (TextView) mainView.findViewById(R.id.day_forecast_pressure),
                (ImageView) mainView.findViewById(R.id.day_forecast_pic)
        );

        hourForecastsViews = new ArrayList<>();
        hourForecastsViews.add(0, new HourForecastView(
                (RelativeLayout) mainView.findViewById(R.id.day_forecast_morning),
                (ImageView) mainView.findViewById(R.id.day_forecast_morning_pic),
                (TextView) mainView.findViewById(R.id.day_forecast_morning_temp)
        ));

        hourForecastsViews.add(0, new HourForecastView(
                (RelativeLayout) mainView.findViewById(R.id.day_forecast_day),
                (ImageView) mainView.findViewById(R.id.day_forecast_day_pic),
                (TextView) mainView.findViewById(R.id.day_forecast_day_temp)
        ));

        hourForecastsViews.add(0, new HourForecastView(
                (RelativeLayout) mainView.findViewById(R.id.day_forecast_evening),
                (ImageView) mainView.findViewById(R.id.day_forecast_evening_pic),
                (TextView) mainView.findViewById(R.id.day_forecast_evening_temp)
        ));

        hourForecastsViews.add(0, new HourForecastView(
                (RelativeLayout) mainView.findViewById(R.id.day_forecast_night),
                (ImageView) mainView.findViewById(R.id.day_forecast_night_pic),
                (TextView) mainView.findViewById(R.id.day_forecast_night_temp)
        ));
    }

    DayForecastView setTitle(@NotNull String title) {
        this.city.setText(title);

        return this;
    }

    DayForecastView setDate(@NotNull LocalDate date) {
        this.dayOfWeek.setText(TimeUtils.getDayOfWeek(date));
        this.date.setText(TimeUtils.getShortDate(date));

        return this;
    }

    void setForecasts(@NotNull List<DetailForecast> detailForecasts) {
        final int currHours = LocalDateTime.now().getHourOfDay();
        for (int i = 0; i < detailForecasts.size(); i++) {

            final DetailForecast forecast = detailForecasts.get(i);
            final HourForecastView view = hourForecastsViews.get(i);

            view.init(
                    forecast,
                    () -> {
                        for (HourForecastView f : hourForecastsViews) {
                            final boolean isSelected = f.equals(view);
                            f.setSelected(context, isSelected);

                            if (isSelected) {
                                selectedHourForecast.init(forecast);
                            }
                        }
                    }
            );

            final boolean isCurrentDayPath = forecast.getDayPeriod().include(currHours);
            view.setSelected(context, isCurrentDayPath);
            if (isCurrentDayPath) {
                selectedHourForecast.init(forecast);
            }
        }

    }

    View getView() {
        return mainView;
    }
}
