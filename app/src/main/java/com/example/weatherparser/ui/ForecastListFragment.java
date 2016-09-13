package com.example.weatherparser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.weatherparser.R;
import com.example.weatherparser.adapters.WeatherViewHolder;
import com.example.weatherparser.adapters.standard.CollectionRecycleAdapter;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.presenter.ForecastListPresenter;
import com.example.weatherparser.mvp.view.ForecastListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastListFragment extends MvpSupportFragment implements ForecastListView {

    private static final String DAY_FORECAST = "DAY_FORECAST";

    @BindView(R.id.fragment_weather_list_recyclerview)
    RecyclerView mWeatherRecyclerView;

    @BindView(R.id.fragment_weather_list_linear_layout_load)
    LinearLayout mLoadView;

    @BindView(R.id.fragment_weather_list_text_view_empty)
    TextView mEmptyView;

    @BindView(R.id.fragment_weather_list_text_view_error)
    TextView mErrorView;

    private CollectionRecycleAdapter<Forecast> mAdapter;

    @InjectPresenter
    ForecastListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        ButterKnife.bind(this, view);

        //Create adapter
        mAdapter = new CollectionRecycleAdapter<Forecast>(getActivity().getBaseContext()) {
            @Override
            public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new WeatherViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_weather, parent, false));
            }
        };

        mWeatherRecyclerView.setAdapter(mAdapter);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mWeatherRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mWeatherRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DayForecastActivity.class);
                        intent.putExtra(DAY_FORECAST, mAdapter.getItem(position));
                        startActivity(intent);
                    }
                })
        );

        return view;
    }

    @Override
    public void showProgress() {
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadView.setVisibility(View.GONE);
    }

    @Override
    public void showForecast() {
        mWeatherRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideForecast() {
        mWeatherRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<Forecast> forecasts) {
        mAdapter.setCollection(forecasts);
    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorView.setVisibility(View.GONE);
    }

    public void updateForecasts() {
        mPresenter.userClickUpdate(getActivity());
    }

}
