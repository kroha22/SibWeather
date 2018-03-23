package com.example.sibweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sibweather.R;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.utils.Action;
import com.example.sibweather.utils.Callable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga
 * on 30.09.2017.
 */
class ForecastListView {
    private final @NotNull View mainView;
    private final @NotNull TextView title;
    private final @NotNull List<LinearLayout> daysForecastViews;

    private Callable<DayForecast> onItemClickListener;
    private Action onChangeCityClickListener;
    private Action onReloadClickListener;

    ForecastListView(@NotNull Context context) {
        mainView = LayoutInflater.from(context).inflate(R.layout.weather_list, null, false);
        title = (TextView) mainView.findViewById(R.id.weather_list_city);

        daysForecastViews = new ArrayList<>();
        daysForecastViews.add(0, (LinearLayout) mainView.findViewById(R.id.weather_list_0));
        daysForecastViews.add(1, (LinearLayout) mainView.findViewById(R.id.weather_list_1));
        daysForecastViews.add(2, (LinearLayout) mainView.findViewById(R.id.weather_list_2));
        daysForecastViews.add(3, (LinearLayout) mainView.findViewById(R.id.weather_list_3));
        daysForecastViews.add(4, (LinearLayout) mainView.findViewById(R.id.weather_list_4));
        daysForecastViews.add(5, (LinearLayout) mainView.findViewById(R.id.weather_list_5));
        daysForecastViews.add(6, (LinearLayout) mainView.findViewById(R.id.weather_list_6));

        final Button changeCityButton = (Button) mainView.findViewById(R.id.weather_list_button_change);
        changeCityButton.setOnClickListener(v -> {
            if (onChangeCityClickListener != null) {
                onChangeCityClickListener.execute();
            }
        });

        final Button updateButton = (Button) mainView.findViewById(R.id.weather_list_button_update);
        updateButton.setOnClickListener(v -> {
            if (onReloadClickListener != null) {
                onReloadClickListener.execute();
            }
        });
    }

    ForecastListView setTitle(@NotNull String title) {
        this.title.setText(title);
        return this;
    }

    ForecastListView setOnChangeCityClickListener(Action onChangeCityClickListener) {
        this.onChangeCityClickListener = onChangeCityClickListener;
        return this;
    }

    ForecastListView setOnReloadClickListener(Action onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
        return this;
    }

    ForecastListView setOnItemClickListener(Callable<DayForecast> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    void setForecasts(@NotNull List<DayForecast> forecasts) {

        for (int i = 0; i < daysForecastViews.size(); i++) {
            final DayForecast forecast = forecasts.get(i);
            final LinearLayout forecastMainView = daysForecastViews.get(i);

            forecastMainView.setVisibility(forecast != null ? View.VISIBLE : View.INVISIBLE);

            if (forecast != null){
                final View view = ViewFactory.getDayWeatherView(mainView.getContext(), forecast);
                view.setOnClickListener(v -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.call(forecast);
                    }
                });

                forecastMainView.removeAllViews();
                forecastMainView.addView(view);
            }
        }
    }

    View getView() {
        return mainView;
    }

}
