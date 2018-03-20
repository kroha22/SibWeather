package com.example.sibweather.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sibweather.R;
import com.example.sibweather.model.DayPeriod;
import com.example.sibweather.model.forecast.DetailForecast;
import com.example.sibweather.model.forecast.Property;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

final class DayForecastView {

    //--------------------------------------------------------------------------------------------
    private static class HourForecast {
        private final LinearLayout mainView;
        private final ImageView img;
        private final TextView temp;

        private HourForecast(LinearLayout mainView, ImageView img, TextView temp) {
            this.mainView = mainView;
            this.img = img;
            this.temp = temp;
        }

        private void init(Context context, DetailForecast forecast, boolean isSelected){
            if (isSelected){

                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mainView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.middle_bubble) );
                } else {
                    mainView.setBackground(ContextCompat.getDrawable(context, R.drawable.middle_bubble));
                }

                final int tempInt = forecast.getTemperature().getAvg();
                final String tempStr = tempInt > 0 ? "+" + tempInt : Integer.toString(tempInt);
                temp.setText(tempStr);

                Picasso.with(context)
                        .load("http:" + forecast.getIconPath())
                        .resize(40, 30)
                        .into(img);
            }
        }
    }
    //--------------------------------------------------------------------------------------------

    private final @NotNull Context mContext;

    private final @NotNull TextView mCity;
    private final @NotNull TextView mDayOfWeek;
    private final @NotNull TextView mDate;

    private final @NotNull TextView mTime;
    private final @NotNull TextView mTemp;
    private final @NotNull TextView mWind;
    private final @NotNull TextView mPress;
    private final @NotNull ImageView mWeatherImg;

    private final @NotNull List<HourForecast> mForecasts;

    private View mMainView;

    DayForecastView(@NotNull Context context) {
        mContext = context;

        mMainView = LayoutInflater.from(context).inflate(R.layout.day_forecast, null, false);

        mCity = (TextView) mMainView.findViewById(R.id.day_forecast_city);
        mDate = (TextView) mMainView.findViewById(R.id.day_forecast_date);
        mDayOfWeek = (TextView) mMainView.findViewById(R.id.day_forecast_day_of_week);
        mTime = (TextView) mMainView.findViewById(R.id.day_forecast_time);
        mTemp = (TextView) mMainView.findViewById(R.id.day_forecast_temp);
        mWind = (TextView) mMainView.findViewById(R.id.day_forecast_wind);
        mPress = (TextView) mMainView.findViewById(R.id.day_forecast_pressure);
        mWeatherImg = (ImageView) mMainView.findViewById(R.id.day_forecast_pic);

        mForecasts = new ArrayList<>();
        mForecasts.add(0, new HourForecast(
                (LinearLayout) mMainView.findViewById(R.id.day_forecast_morning),
                (ImageView) mMainView.findViewById(R.id.day_forecast_morning_pic),
                (TextView) mMainView.findViewById(R.id.day_forecast_morning_temp)
        ));

        mForecasts.add(0, new HourForecast(
                (LinearLayout) mMainView.findViewById(R.id.day_forecast_day),
                (ImageView) mMainView.findViewById(R.id.day_forecast_day_pic),
                (TextView) mMainView.findViewById(R.id.day_forecast_day_temp)
        ));

        mForecasts.add(0, new HourForecast(
                (LinearLayout) mMainView.findViewById(R.id.day_forecast_evening),
                (ImageView) mMainView.findViewById(R.id.day_forecast_evening_pic),
                (TextView) mMainView.findViewById(R.id.day_forecast_evening_temp)
        ));

        mForecasts.add(0, new HourForecast(
                (LinearLayout) mMainView.findViewById(R.id.day_forecast_night),
                (ImageView) mMainView.findViewById(R.id.day_forecast_night_pic),
                (TextView) mMainView.findViewById(R.id.day_forecast_night_temp)
        ));
    }

    void setContent(@NotNull String title, @NotNull String dayOfWeek, @NotNull String date, @NotNull List<DetailForecast> detailForecasts) {
        mCity.setText(title);
        mDayOfWeek.setText(dayOfWeek);
        mDate.setText(date);

        final int currHours = LocalDateTime.now().getHourOfDay();
        for (int i = 0; i < detailForecasts.size(); i++) {
            final DetailForecast forecast = detailForecasts.get(i);
            final DayPeriod dayPeriod = forecast.getDayPeriod();
            final boolean isCurrentDayPath = dayPeriod.include(currHours);

            mForecasts.get(i).init(mContext, forecast, isCurrentDayPath);

            if (isCurrentDayPath){
                Picasso.with(mContext)
                        .load("http:" + forecast.getIconPath())
                        .resize(40, 30)
                        .into(mWeatherImg);

                mTime.setText(dayPeriod.getVal());
                mTemp.setText(getTempDeltaStr(forecast.getTemperature()));
                mWind.setText(forecast.getWind().toString());
                mPress.setText(getPressureStr(forecast.getPressure()));
            }
        }

    }

    @NotNull
    private String getTempDeltaStr(@NotNull Property temp) {
        final int min = temp.getMin();
        String temperature = min > 0 ?  "+" + min : Integer.toString(min);

        final int max = temp.getMax();
        temperature = temperature + " .. " +  ( max > 0 ? "+" + max: Integer.toString(max));

        return temperature;
    }

    @NotNull
    private String getPressureStr(@NotNull Property pressure) {
        return pressure.getAvg() + " мм";
    }

    View getView() {
        return mMainView;
    }
}
