package com.example.weatherparser.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherparser.R;
import com.example.weatherparser.adapters.standard.SimpleViewHolder;
import com.example.weatherparser.mvp.model.DateNames;
import com.example.weatherparser.mvp.model.forecast.Hour;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 09.09.16
 * Time: 11:46
 *
 * @author Olga
 */
public class ForecastViewHolder extends SimpleViewHolder<Hour> {

    @BindView(R.id.item_day_forecast_text_view_time)
    TextView mTimeTextView;

    @BindView(R.id.item_day_forecast_text_view_temp)
    TextView mTempTextView;

    @BindView(R.id.item_day_forecast_image_view_pic)
    ImageView mWeatherImageView;

    @BindView(R.id.item_day_forecast_text_view_wind)
    TextView mWindTextView;

    @BindView(R.id.item_day_forecast_text_view_pressure)
    TextView mPressureTextView;

    public ForecastViewHolder(View rootView) {
        super(rootView);
    }

    @Override
    protected void create(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void bind(Hour model) {

        Picasso.with(getRoot().getContext())
                .load("http:" + model.getIcon_path())
                .resize(40, 30)
                .into(mWeatherImageView);

        mTimeTextView.setText(DateNames.getTime().get(model.getHour() / 6).getName());

        String temperature;
        int t = model.getTemperature().getMin();
        if (t > 0) {
            temperature = "+" + t;
        } else {
            temperature = Integer.toString(t);
        }

        t = model.getTemperature().getMax();
        if (t > 0) {
            temperature = temperature + " .. +" + t;
        } else {
            temperature = temperature + " .. " + t;
        }

        mTempTextView.setText(temperature);

        String wind = model.getWind().getSpeed().getMin() + "-" + model.getWind().getSpeed().getMax()
                + " " + model.getWind().getDirection().getTitleShort();
        mWindTextView.setText(wind);

        String pressure = model.getPressure().getAvg() + " мм";
        mPressureTextView.setText(pressure);

    }
}
