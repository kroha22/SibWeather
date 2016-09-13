package com.example.weatherparser.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.weatherparser.mvp.ClearStrategy;
import com.example.weatherparser.mvp.model.forecast.Forecast;

import java.util.List;

/**
 * Date: 09.09.16
 * Time: 16:06
 *
 * @author Olga
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ForecastListView extends MvpView {

    String PROGRESS_TAG = "ERROR_TAG";

    // ProgressBar
    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = PROGRESS_TAG)
    void showProgress();

    @StateStrategyType(value = ClearStrategy.class, tag = PROGRESS_TAG)
    void hideProgress();

    void showForecast();

    void hideForecast();

    void setData(List<Forecast> forecasts);

    //Empty and error
    void showEmpty();

    void hideEmpty();

    void showError();

    void hideError();

}

