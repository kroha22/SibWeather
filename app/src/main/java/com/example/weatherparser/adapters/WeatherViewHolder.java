package com.example.weatherparser.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherparser.R;
import com.example.weatherparser.adapters.standard.CollectionRecycleAdapter;
import com.example.weatherparser.mvp.model.forecast.Forecast;
import com.example.weatherparser.mvp.model.forecast.Hour;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 08.09.16
 * Time: 19:30
 *
 * @author Olga
 */
public class WeatherViewHolder extends CollectionRecycleAdapter.RecycleViewHolder<Forecast> {

    @BindView(R.id.item_weather_text_view_day)
    TextView mDayTextView;

    @BindView(R.id.item_weather_text_view_date)
    TextView mDateTextView;

    @BindView(R.id.item_weather_text_view_temp)
    TextView mTempTextView;

    @BindView(R.id.item_weather_image_view_pic)
    ImageView mWeatherImageView;

    @BindView(R.id.item_weather_text_view_wind)
    TextView mWindTextView;

    @BindView(R.id.item_weather_text_view_pressure)
    TextView mPressureTextView;

    public WeatherViewHolder(View rootView) {
        super(rootView);
    }

    @Override
    protected void create(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bind(Forecast model) {
        try {
            Calendar mDate = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            mDate.setTime(sdf.parse(model.getDate()));

            String s = String.format("%tA", mDate);
            mDayTextView.setText(s);

            s = String.format(Locale.ROOT, "%td %tB", mDate, mDate);
            mDateTextView.setText(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //set values
        Hour day = model.getHours().get(2);
        String temperature;
        int t = day.getTemperature().getAvg();
        if (t > 0) {
            temperature = "+" + t;
        } else {
            temperature = Integer.toString(t);
        }
        mTempTextView.setText(temperature);

        String wind = day.getWind().getSpeed().getMin() + "-" + day.getWind().getSpeed().getMax()
                + " " + day.getWind().getDirection().getTitleShort();
        mWindTextView.setText(wind);

        String pressure = day.getPressure().getAvg() + " мм";
        mPressureTextView.setText(pressure);

        Picasso.with(getRoot().getContext())
                .load("http:" + day.getIcon_path())
                .resize(40, 30)
                .into(mWeatherImageView);

    }

}

