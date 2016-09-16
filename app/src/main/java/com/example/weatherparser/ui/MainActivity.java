package com.example.weatherparser.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

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

    @BindView(R.id.activity_main_spinner)
    Spinner mCitiesSpinner;

    //Fragments
    private static ForecastListFragment mWeatherListFragment;
    private static FragmentManager mFragmentManager;

    private ArrayAdapter<String> mAdapter;

    @InjectPresenter
    WeatherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mUpdateButton.setEnabled(false);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        mCitiesSpinner.setAdapter(mAdapter);
        mCitiesSpinner.setPrompt(getString(R.string.select_city));
        mCitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mUpdateButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mUpdateButton.setEnabled(false);
            }
        });

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
                mWeatherListFragment.updateForecasts(mCitiesSpinner.getSelectedItemPosition());
            }
        });
    }

    @Override
    public void setCollection(String[] cities) {
        mAdapter.addAll(cities);
    }
}