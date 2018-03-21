package com.example.sibweather.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.sibweather.R;
import com.example.sibweather.model.City;
import com.example.sibweather.model.forecast.DayForecast;
import com.example.sibweather.model.forecast.Db;
import com.example.sibweather.network.RetrofitService;
import com.example.sibweather.utils.Action;
import com.example.sibweather.utils.Callable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public class MainActivity extends MvpAppCompatActivity implements ForecastView {
    //---------------------------------------------------------------------------------------------
    private final static String CURRENT_CITY = "current_city";
    //---------------------------------------------------------------------------------------------

    private FrameLayout viewContainer;

    @InjectPresenter
    ForecastPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewContainer = (FrameLayout) findViewById(R.id.activity_main_container);

        presenter.init(RetrofitService.getInstance(this), new Db.DAO(this), getCurrentCity());
        presenter.start();
    }

    @Override
    public void onBackPressed() {
        if (!presenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void showLoadProgress() {
        showView(ViewFactory.getProgressView(this));
    }

    @Override
    public void showForecast(@NotNull City city, @NotNull List<DayForecast> forecasts) {
        saveCurrentCity(city);

        final ForecastListView forecastListView = new ForecastListView(this)
                .setTitle(city.getVal())
                .setOnItemClickListener(presenter::userSelectDay)
                .setOnChangeCityClickListener(presenter::userClickSelectCity)
                .setOnReloadClickListener(presenter::userClickReload);

        forecastListView.setForecasts(forecasts);

        showView(forecastListView.getView());
    }

    @Override
    public void showDayForecast(@NotNull String title, @NotNull DayForecast dayForecast) {
        final DayForecastView dayForecastView = new DayForecastView(this)
                .setTitle(title)
                .setDate(dayForecast.getDate());

        dayForecastView.setForecasts(dayForecast.getDetail());

        showView(dayForecastView.getView());
    }

    @Override
    public void showSelectDialog(@NotNull String[] cities, @NotNull Callable<String> onClick){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.select_city).
                setItems(cities, (dialog, which) -> onClick.call(cities[which])).
                create().
                show();
    }

    @Override
    public void showDialog(@NotNull String msg, @NotNull Action onPositive, @Nullable Action onNegative){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setPositiveButton("Да", (dialog, which) -> onPositive.execute());

        if (onNegative!=null) {
            builder.setPositiveButton("Нет", (dialog, which) -> onNegative.execute());
        }

        builder.create().show();
    }

    private void showView(@NotNull View view) {
        viewContainer.removeAllViewsInLayout();
        viewContainer.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void saveCurrentCity(@NotNull City city){
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CURRENT_CITY, city.name());
        editor.apply();
    }

    @Nullable
    private City getCurrentCity(){
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final String city = sharedPref.getString(CURRENT_CITY, null);
        return city != null ? City.valueOf(city) : null;
    }

}