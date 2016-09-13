package com.example.weatherparser.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.weatherparser.mvp.model.forecast.Hour;

import java.util.List;

/**
 * Date: 12.09.16
 * Time: 12:12
 *
 * @author Olga
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface DayForecastView  extends MvpView {

    void setDate(String dayOfWeek, String date);

    void setForecasts(List<Hour> hours);

}
