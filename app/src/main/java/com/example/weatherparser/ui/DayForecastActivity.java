package com.example.weatherparser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.weatherparser.R;
import com.example.weatherparser.adapters.ForecastViewHolder;
import com.example.weatherparser.adapters.standard.SimpleAdapter;
import com.example.weatherparser.adapters.standard.SimpleViewHolder;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.model.forecast.Hour;
import com.example.weatherparser.mvp.presenter.DayForecastPresenter;
import com.example.weatherparser.mvp.view.DayForecastView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayForecastActivity extends MvpAppCompatActivity implements DayForecastView {

    private static final String DAY_FORECAST = "DAY_FORECAST";

    @BindView(R.id.activity_day_forecast_text_view_day)
    TextView mDayTextView;

    @BindView(R.id.activity_day_forecast_text_view_date)
    TextView mDateTextView;

    @BindView(R.id.activity_day_forecast_list_view_forecast)
    ListView mForecastList;

    private Forecast mDayForecast;
    private SimpleAdapter<Hour> mAdapter;

    @InjectPresenter
    DayForecastPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_forecast);
        ButterKnife.bind(this);

        //get forecast
        Intent intent = getIntent();
        if (intent != null) {
            mDayForecast = (Forecast) intent.getSerializableExtra(DAY_FORECAST);
        }

        mAdapter = new SimpleAdapter<Hour>(this) {
            @Override
            protected SimpleViewHolder<Hour> createViewHolder(ViewGroup parent, int viewType) {
                return new ForecastViewHolder(mInflater.inflate(R.layout.item_day_forecast, parent, false));
            }
        };

        mForecastList.setAdapter(mAdapter);

        mPresenter.userSelectDay(mDayForecast);
    }

    @Override
    public void setDate(String dayOfWeek, String date) {
        mDayTextView.setText(dayOfWeek);
        mDateTextView.setText(date);
    }

    @Override
    public void setForecasts(List<Hour> hours) {
        mAdapter.setCollection(hours);
    }
}
