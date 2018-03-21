package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Olga
 * on 30.09.2017.
 */
public class WeatherResponse {

    @SerializedName("metadata")
    private Metadata metadata;

    @SerializedName("forecasts")
    private List<Forecast> forecasts;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
