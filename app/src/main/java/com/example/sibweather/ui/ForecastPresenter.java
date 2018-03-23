package com.example.sibweather.ui;

import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.core.deps.guava.collect.Lists;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.sibweather.model.City;
import com.example.sibweather.model.DayPeriod;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.model.forecast.Db;
import com.example.sibweather.model.forecast.DetailForecast;
import com.example.sibweather.model.forecast.Forecast;
import com.example.sibweather.model.forecast.Hour;
import com.example.sibweather.model.forecast.WeatherResponse;
import com.example.sibweather.network.RetrofitService;
import com.example.sibweather.utils.Action;
import com.example.sibweather.utils.TimeUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Olga
 * on 30.09.2017.
 */
@InjectViewState
public class ForecastPresenter extends MvpPresenter<ForecastView> {
    //-------------------------------------------------------------------------------------------
    private static final String LOAD_ERROR_MESSAGE = "Во время загрузки произошла ошибка.";
    private static final String NO_DATA_MESSAGE = "Отсутствуют данные для отображения.";
    private static final String RELOAD_REQUEST = "Повторить загруку?";
    private static final String WEATHER_IN_STR = "Погода в ";

    //-------------------------------------------------------------------------------------------
    private enum State {
        EMPTY, MESSAGE, LOAD, SELECT, FORECASTS_LIST, DAY_FORECAST
    }
    //-------------------------------------------------------------------------------------------

    private RetrofitService dataLoader;
    private Db.DAO dao;

    private City city;
    private String weatherInCityStr;
    private List<DayForecast> forecasts;
    private DayForecast selectedDayForecast;

    private Subscription subscription;
    private State currentState;

    void init(@NotNull RetrofitService retrofitService, @NotNull Db.DAO dao, @Nullable City currentCity) {
        this.dataLoader = retrofitService;
        this.dao = dao;
        this.currentState = State.EMPTY;
        this.city = currentCity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    void start() {
        if (currentState == State.EMPTY) {
            if (city != null) {
                getForecasts(() -> {
                    if ((forecasts.isEmpty())) {
                        loadForecasts();
                    } else {
                        showForecast();
                    }
                });
            } else {
                showSelectDialog();
            }
        }
    }

    void userClickSelectCity() {
        showSelectDialog();
    }

    void userClickReload() {
        loadForecasts();
    }

    void userSelectDay(@NotNull DayForecast forecast) {
        this.selectedDayForecast = forecast;
        showDayForecast();
    }

    boolean onBackPressed() {
        switch (currentState) {

            case DAY_FORECAST:
                showForecast(this::loadForecasts);
                return true;

        }

        return false;
    }

    private void getForecasts(@NotNull Action onComplete) {
        showLoadProgress();

        subscription = loadForecastsFromDb()
                .timeout(1, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .doOnNext(forecasts -> dao.saveForecasts(city, forecasts))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setForecasts, this::onLoadError, onComplete::execute);
    }

    private void loadForecasts() {
        showLoadProgress();

        subscription = dataLoader.getWeather(city)
                .timeout(1, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .map(WeatherResponse::getForecasts)
                .map(ForecastPresenter::getDayForecasts)
                .doOnNext(forecasts -> dao.saveForecasts(city, forecasts))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setForecasts, this::onLoadError, this::showForecast);
    }

    private void showLoadProgress() {
        getViewState().showLoadProgress();
        setCurrentState(State.LOAD);
    }

    private void showSelectDialog() {
        getViewState().showSelectDialog(City.getAllCitiesNames(), city -> onSelectCity(City.getCity(city)));
        setCurrentState(State.SELECT);
    }

    private void showForecast(@NotNull Action onEmpty) {
        if ((forecasts.isEmpty())) {
            onEmpty.execute();
        } else {
            showForecast();
        }
    }

    private void showForecast() {
        if ((forecasts.isEmpty())) {
            showReloadMsg(NO_DATA_MESSAGE);
        } else {
            getViewState().showForecast(city, forecasts);
            setCurrentState(State.FORECASTS_LIST);
        }
    }

    private void showReloadMsg(@NotNull String errMsg) {
        getViewState().showDialog(errMsg + " " + RELOAD_REQUEST, this::loadForecasts, null);
        setCurrentState(State.MESSAGE);
    }

    private void showDayForecast() {
        getViewState().showDayForecast(weatherInCityStr, selectedDayForecast);
        setCurrentState(State.DAY_FORECAST);
    }

    private void onSelectCity(@NotNull City city) {
        this.city = city;
        this.weatherInCityStr = WEATHER_IN_STR + city.getVal() + (city == City.KEMEROVO ? "" : "е");

        loadForecasts();
    }

    private void onLoadError(Throwable err) {
        err.printStackTrace();
        showReloadMsg(LOAD_ERROR_MESSAGE);
    }

    private Observable<List<DayForecast>> loadForecastsFromDb() {
        return Observable.create(subscriber -> {
            subscriber.onNext(dao.getForecasts(city));
            subscriber.onCompleted();
        });
    }

    private void setForecasts(@NotNull List<DayForecast> forecasts) {
        this.forecasts = forecasts;
    }

    private void setCurrentState(@NotNull State currentState) {
        this.currentState = currentState;
    }

    private static List<DayForecast> getDayForecasts(@Nullable List<Forecast> forecasts) {
        return forecasts != null ?
                Lists.newArrayList(Iterables.transform(forecasts, ForecastPresenter::getDayForecast)) :
                Collections.emptyList();
    }

    private static DayForecast getDayForecast(@NotNull Forecast forecast) {
        return new DayForecast(
                TimeUtils.getLocalDate(forecast.getDate()),
                Lists.newArrayList(Iterables.transform(forecast.getHours(), ForecastPresenter::getDetailForecast))
        );
    }

    private static DetailForecast getDetailForecast(@NotNull Hour hour) {
        return new DetailForecast(
                DayPeriod.getByHours(hour.getHour()),
                hour.getTemperature(),
                hour.getPressure(),
                hour.getWind(),
                hour.getIconPath(),
                hour.getBigIconPath()
        );
    }
    //---------------------------------------------------------------------------------------------
}
