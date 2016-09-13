package com.example.weatherparser.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.model.forecast.WeatherResponse;
import com.example.weatherparser.mvp.view.ForecastListView;
import com.example.weatherparser.network.RetrofitService;
import com.example.weatherparser.network.WeatherApi;

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

    private String CITY = "novosibirsk";
    private Context mContext;
    private Subscription mSubscription;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().hideError();
        getViewState().hideProgress();
        getViewState().hideForecast();
        getViewState().showEmpty();
    }

    public void userClickUpdate(Context context) {
        mContext = context;
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideProgress();
                        getViewState().showError();
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
                            getViewState().setData(forecasts);
                        }
                    }
                });
    }

    private Observable<WeatherResponse> getForecastList() {

        return RetrofitService.getInstance(mContext)
                .createApiService(WeatherApi.class)
                .getForecast(CITY);
    }

}
