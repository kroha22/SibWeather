package com.example.weatherparser.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.weatherparser.mvp.model.CityNames;
import com.example.weatherparser.mvp.view.WeatherView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 09.09.16
 * Time: 17:43
 *
 * @author Olga
 */
@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        List<CityNames> cityNames = new ArrayList<>();
        cityNames.addAll(CityNames.getCities());
        String[] cities = new String[cityNames.size()];
        for (int i = 0; i < cityNames.size(); i++) {
            cities[i] = cityNames.get(i).getName();
        }
        getViewState().setCollection(cities);
    }
}
