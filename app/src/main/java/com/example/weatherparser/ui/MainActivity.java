package com.example.weatherparser.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.weatherparser.R;
import com.example.weatherparser.mvp.presenter.WeatherPresenter;
import com.example.weatherparser.mvp.view.WeatherView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements WeatherView {

    @BindView(R.id.activity_main_frame_layout_container)
    FrameLayout mContainer;

    @BindView(R.id.activity_main_button_update)
    Button mUpdateButton;

    //Fragments
    private static ForecastListFragment mWeatherListFragment;
    private static FragmentManager mFragmentManager;


    @InjectPresenter
    WeatherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //init fragments
        mWeatherListFragment = new ForecastListFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame_layout_container, mWeatherListFragment)
                .addToBackStack(null)
                .commit();

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeatherListFragment.updateForecasts();
            }
        });
    }
}