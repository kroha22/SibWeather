package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Date: 08.09.16
 * Time: 20:19
 *
 * @author Olga
 */
public class WeatherResponse {

    @SerializedName("metadata")
    private Metadata mMetadata;

    @SerializedName("forecasts")
    private List<Forecast> mForecasts;

    public Metadata getMetadata() {
        return mMetadata;
    }

    public void setMetadata(Metadata metadata) {
        mMetadata = metadata;
    }

    public List<Forecast> getForecasts() {
        return mForecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        mForecasts = forecasts;
    }
}
