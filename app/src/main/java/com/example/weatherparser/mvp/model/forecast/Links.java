package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Links implements Serializable {

    @SerializedName("city")
    private String mCity;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }
}
