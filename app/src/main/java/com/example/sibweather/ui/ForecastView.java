package com.example.sibweather.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.sibweather.model.City;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.model.forecast.DetailForecast;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Date: 12.09.16
 * Time: 12:12
 *
 * @author Olga
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ForecastView extends MvpView {

    String PROGRESS_TAG = "ERROR_TAG";

    void showSelectDialog(@NotNull String[] cities);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = PROGRESS_TAG)
    void showLoadProgress();

    void showForecast(@NotNull City city, @NotNull String[] cities, @NotNull List<DayForecast> forecasts);

    void showMessage(@NotNull String message);

    void showDayForecast(@NotNull String title, @NotNull LocalDate day, @NotNull List<DetailForecast> forecasts);

}
