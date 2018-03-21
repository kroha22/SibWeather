package com.example.sibweather.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.sibweather.model.City;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.utils.Action;
import com.example.sibweather.utils.Callable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Olga
 * on 30.09.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ForecastView extends MvpView {

    String PROGRESS_TAG = "ERROR_TAG";

    void showSelectDialog(@NotNull String[] cities, @NotNull Callable<String> onClick);

    void showDialog(@NotNull String msg, @NotNull Action onPositive, @Nullable Action onNegative);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = PROGRESS_TAG)
    void showLoadProgress();

    void showForecast(@NotNull City city, @NotNull List<DayForecast> forecasts);

    void showDayForecast(@NotNull String title, @NotNull DayForecast dayForecast);

}
