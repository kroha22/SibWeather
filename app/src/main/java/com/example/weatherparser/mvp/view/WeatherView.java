package com.example.weatherparser.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Date: 09.09.16
 * Time: 17:43
 *
 * @author Olga
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
}
