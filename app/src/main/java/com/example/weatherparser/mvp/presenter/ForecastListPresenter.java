package com.example.weatherparser.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.weatherparser.mvp.model.CityNames;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.model.forecast.WeatherResponse;
import com.example.weatherparser.mvp.view.ForecastListView;
import com.example.weatherparser.network.RetrofitService;
import com.example.weatherparser.network.WeatherApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Date: 09.09.16
 * Time: 16:06
 *
 * @author Olga
 */
@InjectViewState
public class ForecastListPresenter extends MvpPresenter<ForecastListView> {

    private String mCity;
    private Context mContext;
    private List<Forecast> mForecasts;
    private Subscription mSubscription;
    private boolean mError;

    public void userSelectCity() {
        if (mForecasts != null) {
            getViewState().setData(mForecasts);
        } else {
            if (mError) {
                getViewState().showError();
                getViewState().hideProgress();
                getViewState().hideForecast();
                getViewState().hideEmpty();
            }
            getViewState().hideError();
            getViewState().hideProgress();
            getViewState().hideForecast();
            getViewState().showEmpty();
        }
    }

    public void userClickUpdate(int number, Context context) {
        mContext = context;

        CityNames cityName = CityNames.getCities().get(number);
        String cityRu = cityName.getName();
        mCity = cityName.name().toLowerCase();

        String weatherInCity;
        if (cityRu.equals(CityNames.KEMEROVO.getName())) {
            weatherInCity = "Погода в " + cityRu;
        } else {
            weatherInCity = "Погода в " + cityRu + "е";
        }
        getViewState().setCityName(number, weatherInCity);

        getViewState().hideError();
        getViewState().hideForecast();
        reloadForecasts();
    }

    private void reloadForecasts() {
        getViewState().hideEmpty();
        getViewState().showProgress();

        mSubscription = getForecastList()
                .timeout(1, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherResponse, List<Forecast>>() {
                    @Override
                    public List<Forecast> call(WeatherResponse weatherResponse) {
                        return weatherResponse.getForecasts();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Forecast>>() {
                    @Override
                    public void onCompleted() {
                        getViewState().hideProgress();
                        getViewState().showForecast();
                        mError = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideProgress();
                        getViewState().showError();
                        mError = true;
                    }

                    @Override
                    public void onNext(List<Forecast> forecasts) {
                        if ((forecasts.isEmpty())) {
                            getViewState().hideError();
                            getViewState().showEmpty();
                            getViewState().hideProgress();
                        } else {
                            getViewState().hideEmpty();
                            getViewState().hideError();
                            mForecasts = forecasts;
                            getViewState().setData(mForecasts);
                        }
                    }
                });
    }

    private Observable<WeatherResponse> getForecastList() {

        return RetrofitService.getInstance(mContext)
                .createApiService(WeatherApi.class)
                .getForecast(mCity);
    }

}
