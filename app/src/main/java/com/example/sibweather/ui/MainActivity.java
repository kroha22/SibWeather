package com.example.sibweather.ui;

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
import com.example.sibweather.model.forecast.DetailForecast;
import com.example.sibweather.network.RetrofitService;
import com.example.sibweather.utils.TimeUtils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements ForecastView {

    private FrameLayout mContainer;

    @InjectPresenter
    ForecastPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContainer = (FrameLayout) findViewById(R.id.activity_main_container);

        mPresenter.init(RetrofitService.getInstance(this), new Db.DAO(this));
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (!mPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void showView(@NotNull View view) {
        mContainer.removeAllViews();
        mContainer.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void showLoadProgress() {
        showView(ViewFactory.getProgressView(this));
    }

    @Override
    public void showForecast(@NotNull City city, @NotNull String[] cities, @NotNull List<DayForecast> forecasts) {

        final ForecastListView mForecastListView = new ForecastListView(this);
        mForecastListView.setOnItemClickListener(mPresenter::userSelectDay);
        mForecastListView.setOnReloadClickListener(mPresenter::userSelectCity);
        mForecastListView.setContent(cities, forecasts);

        showView(mForecastListView.getView());
    }

    @Override
    public void showMessage(@NotNull String message) {
        showView(ViewFactory.getMessageView(this, message));
    }

    @Override
    public void showDayForecast(@NotNull String title, @NotNull LocalDate day, @NotNull List<DetailForecast> forecasts) {

        final DayForecastView dayForecastView = new DayForecastView(this);
        dayForecastView.setContent(title, TimeUtils.getDayOfWeek(day), TimeUtils.getDate(day), forecasts);

        showView(dayForecastView.getView());
    }

    @Override
    public void showSelectDialog(@NotNull String[] cities){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.select_city).
                setItems(cities, (dialog, which) -> mPresenter.userSelectCity(cities[which])).
                create().
                show();
    }

}