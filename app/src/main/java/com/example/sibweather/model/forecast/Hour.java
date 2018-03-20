package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hour implements Serializable {

    @SerializedName("hour")
    private int mHour;

    @SerializedName("temperature")
    private Property mTemperature;

    @SerializedName("pressure")
    private Property mPressure;

    @SerializedName("humidity")
    private Property mHumidity;

    @SerializedName("wind")
    private Wind mWind;

    @SerializedName("cloud")
    private Atmosphere mCloud;

    @SerializedName("precipitation")
    private Atmosphere mPrecipitation;

    @SerializedName("icon")
    private String mIcon;

    @SerializedName("icon_path")
    private String mIcon_path;

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public Property getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Property temperature) {
        mTemperature = temperature;
    }

    public Property getPressure() {
        return mPressure;
    }

    public void setPressure(Property pressure) {
        mPressure = pressure;
    }

    public Property getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Property humidity) {
        mHumidity = humidity;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public Atmosphere getCloud() {
        return mCloud;
    }

    public void setCloud(Atmosphere cloud) {
        mCloud = cloud;
    }

    public Atmosphere getPrecipitation() {
        return mPrecipitation;
    }

    public void setPrecipitation(Atmosphere precipitation) {
        mPrecipitation = precipitation;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIcon_path() {
        return mIcon_path;
    }

    public void setIcon_path(String icon_path) {
        mIcon_path = icon_path;
    }
}
