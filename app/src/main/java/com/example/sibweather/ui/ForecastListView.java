package com.example.sibweather.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.sibweather.R;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.utils.Callable;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

class ForecastListView {
    private final @NotNull Context mContext;

    private final @NotNull Spinner mCitiesSpinner;
    private final @NotNull Button mUpdateButton;
    private final @NotNull List<LinearLayout> mDaysForecasts;

    private View mMainView;
    private Callable<DayForecast> onItemClickListener;
    private Callable<String> onReloadClickListener;
    private ArrayAdapter<String> mCitiesAdapter;

    ForecastListView(@NotNull Context context) {
        mContext = context;

        mMainView = LayoutInflater.from(context).inflate(R.layout.weather_list, null, false);

        mDaysForecasts = new ArrayList<>();
        mDaysForecasts.add(0, (LinearLayout) mMainView.findViewById(R.id.weather_list_0));
        mDaysForecasts.add(1, (LinearLayout) mMainView.findViewById(R.id.weather_list_1));
        mDaysForecasts.add(2, (LinearLayout) mMainView.findViewById(R.id.weather_list_2));
        mDaysForecasts.add(3, (LinearLayout) mMainView.findViewById(R.id.weather_list_3));
        mDaysForecasts.add(4, (LinearLayout) mMainView.findViewById(R.id.weather_list_4));
        mDaysForecasts.add(5, (LinearLayout) mMainView.findViewById(R.id.weather_list_5));
        mDaysForecasts.add(6, (LinearLayout) mMainView.findViewById(R.id.weather_list_6));

        mCitiesSpinner = (Spinner) mMainView.findViewById(R.id.weather_list_cities);
        mCitiesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
        mCitiesSpinner.setAdapter(mCitiesAdapter);
        mCitiesSpinner.setPrompt(context.getResources().getString(R.string.select_city));
        mCitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUpdateButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mUpdateButton.setEnabled(false);
            }
        });

        mUpdateButton = (Button) mMainView.findViewById(R.id.weather_list_button_update);
        mUpdateButton.setOnClickListener(v -> {
            if (onReloadClickListener != null) {
                onReloadClickListener.call(mCitiesSpinner.getSelectedItem().toString());
            }
        });
        mUpdateButton.setEnabled(false);

    }

    void setOnReloadClickListener(Callable<String> onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
    }

    void setOnItemClickListener(Callable<DayForecast> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void setContent(@NotNull String[] cities, @NotNull List<DayForecast> forecasts) {
        mCitiesAdapter.addAll(cities);

        final LocalDate today = LocalDate.now();

        for (int i = 0; i < forecasts.size(); i++) {
            final DayForecast forecast = forecasts.get(i);

            final View view = getDayWeatherView(today, forecast);
            view.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.call(forecast);
                }
            });

            mDaysForecasts.get(i).removeAllViews();
            mDaysForecasts.get(i).addView(view);
        }
    }

    @NotNull
    private View getDayWeatherView(@NotNull LocalDate today, @NotNull DayForecast forecast) {
        if (forecast.getDate().dayOfMonth().equals(today.dayOfMonth()) &&
                forecast.getDate().monthOfYear().equals(today.monthOfYear())){
            return WeatherView.getTodayWeatherView(mContext, forecast);
        } else {
            return WeatherView.getDayWeatherView(mContext, forecast);
        }
    }

    View getView() {
        return mMainView;
    }

}
