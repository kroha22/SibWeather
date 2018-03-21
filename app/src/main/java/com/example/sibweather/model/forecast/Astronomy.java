package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Astronomy {
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("length_day_human")
    private String lengthDayHuman;
    @SerializedName("moon_illuminated")
    private int moonIlluminated;
    @SerializedName("moon_phase")
    private String moonPhase;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getLengthDayHuman() {
        return lengthDayHuman;
    }

    public void setLengthDayHuman(String lengthDayHuman) {
        this.lengthDayHuman = lengthDayHuman;
    }

    public int getMoonIlluminated() {
        return moonIlluminated;
    }

    public void setMoonIlluminated(int moonIlluminated) {
        this.moonIlluminated = moonIlluminated;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }
}
