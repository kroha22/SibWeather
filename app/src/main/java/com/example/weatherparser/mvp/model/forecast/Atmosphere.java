package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Atmosphere implements Serializable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("value")
    private String mValue;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
