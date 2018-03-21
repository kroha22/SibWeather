package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Hour implements Serializable {

    @SerializedName("hour")
    private int hour;

    @SerializedName("temperature")
    private Property temperature;

    @SerializedName("pressure")
    private Property pressure;

    @SerializedName("humidity")
    private Property humidity;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("cloud")
    private Atmosphere cloud;

    @SerializedName("precipitation")
    private Atmosphere precipitation;

    @SerializedName("icon")
    private String icon;

    @SerializedName("icon_path")
    private String iconPath;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Property getTemperature() {
        return temperature;
    }

    public void setTemperature(Property temperature) {
        this.temperature = temperature;
    }

    public Property getPressure() {
        return pressure;
    }

    public void setPressure(Property pressure) {
        this.pressure = pressure;
    }

    public Property getHumidity() {
        return humidity;
    }

    public void setHumidity(Property humidity) {
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Atmosphere getCloud() {
        return cloud;
    }

    public void setCloud(Atmosphere cloud) {
        this.cloud = cloud;
    }

    public Atmosphere getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Atmosphere precipitation) {
        this.precipitation = precipitation;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_path() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath.replace("small", "big");
    }

    public String getBigIconPath() {
        return iconPath.replace("small", "big-icons");
    }
}
