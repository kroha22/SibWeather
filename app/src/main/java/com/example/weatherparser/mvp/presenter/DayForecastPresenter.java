package com.example.weatherparser.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.weatherparser.mvp.model.CityNames;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.view.DayForecastView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date: 12.09.16
 * Time: 12:12
 *
 * @author Olga
 */
@InjectViewState
public class DayForecastPresenter extends MvpPresenter<DayForecastView> {

    public void userSelectDay(int number, Forecast forecast) {

        String city = CityNames.getCities().get(number).getName();
        String weatherInCity;
        if (city.equals(CityNames.KEMEROVO.getName())){
            weatherInCity = "Погода в " + city;
        } else {
            weatherInCity = "Погода в " + city + "е";
        }
        getViewState().setCityName(weatherInCity);

        try {
            Calendar mDate = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            mDate.setTime(sdf.parse(forecast.getDate()));
            String day = String.format("%tA", mDate);
            String date = String.format(Locale.ROOT, "%td %tB", mDate, mDate);
            getViewState().setDate(day, date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        getViewState().setForecasts(forecast.getHours());

    }
}
