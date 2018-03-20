package com.example.sibweather.ui;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.sibweather.model.City;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.model.forecast.Db;
import com.example.sibweather.network.RetrofitService;
import com.example.sibweather.network.WeatherApi;
import com.example.sibweather.model.forecast.WeatherResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Date: 12.09.16
 * Time: 12:12
 *
 * @author Olga
 */
@InjectViewState
public class ForecastPresenter extends MvpPresenter<ForecastView> {
    //-------------------------------------------------------------------------------------------
    private static final String LOAD_ERROR_MESSAGE = "Во время загрузки произошла ошибка";
    private static final String NO_DATA_MESSAGE = "Нет данных";
    private static final String WEATHER_IN_STR = "Погода в ";
    //-------------------------------------------------------------------------------------------
    private enum State {
        EMPTY, ERROR, LOAD, SELECT, FORECASTS_LIST, DAY_FORECAST
    }
    //-------------------------------------------------------------------------------------------

    private RetrofitService mRetrofitService;
    private Db.DAO mDao;
    private String[] mCities;

    private City mCity;
    private String mWeatherInCity;
    private List<DayForecast> mForecasts;
    private Subscription mSubscription;

    private State mCurrentState;

    void init(@NotNull RetrofitService retrofitService, @NotNull Db.DAO dao) {
        mRetrofitService = retrofitService;
        mDao = dao;
        mCities = getCitiesNames();
    }

    @NonNull
    private String[] getCitiesNames() {
        final City[] cities = City.values();

        final String[] names = new String[cities.length];

        for (int i = 0; i < cities.length; i++) {
            names[i] = cities[i].getVal();
        }
        return names;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    void start() {
        if (mCurrentState == State.EMPTY){
            showSelectDialog();
        }
    }

    void userSelectCity(@NotNull String city) {
        mCity = City.getCity(city);
        mWeatherInCity = WEATHER_IN_STR + mCity + (mCity == City.KEMEROVO ? "" : "е");
        loadForecasts();
    }

    void userSelectDay(@NotNull DayForecast forecast) {
        showDayForecast(forecast);
    }

    boolean onBackPressed() {
        switch (mCurrentState) {

            case ERROR: case DAY_FORECAST:
                showForecast();
                return true;

            case FORECASTS_LIST:
                showSelectDialog();
                return true;
        }

        return false;
    }

    private void loadForecasts() {
        showProgress();

        mSubscription = getForecastList()
                .timeout(1, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .map(WeatherResponse::getDayForecasts)
                .doOnNext(forecasts -> mDao.saveForecasts(mCity, forecasts))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( this::setForecasts, this::onLoadError, this::showForecast );
    }

    private void setForecasts(List<DayForecast> forecasts) {
        mForecasts = forecasts;
    }

    private void onLoadError(@NotNull Throwable throwable) {
        showLoadError();
    }

    private Observable<WeatherResponse> getForecastList() {
        return mRetrofitService
                .createApiService(WeatherApi.class)
                .getForecast(mCity.name().toLowerCase());
    }

    private void showSelectDialog() {
        getViewState().showSelectDialog(mCities);
        setCurrentState(State.SELECT);
    }

    private void showForecast() {
        if ((mForecasts.isEmpty())) {
            getViewState().showMessage(NO_DATA_MESSAGE);
            return;
        }
        getViewState().showForecast(mCity, mCities, mForecasts);
        setCurrentState(State.FORECASTS_LIST);
    }

    private void showDayForecast(@NotNull DayForecast forecast) {
        getViewState().showDayForecast(mWeatherInCity, forecast.getDate(), forecast.getDetail());
        setCurrentState(State.DAY_FORECAST);
    }

    private void showProgress() {
        getViewState().showLoadProgress();
        setCurrentState(State.LOAD);
    }

    private void showLoadError() {
        getViewState().showMessage(LOAD_ERROR_MESSAGE);
        setCurrentState(State.ERROR);
    }

    private void setCurrentState(@NotNull State currentState) {
        mCurrentState = currentState;
    }
}
