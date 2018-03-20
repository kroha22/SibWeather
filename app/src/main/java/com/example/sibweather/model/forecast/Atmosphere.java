package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Atmosphere implements Serializable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("value")
    private String mValue;

    public Atmosphere() {/**/}

    public Atmosphere(String mTitle, String mValue) {
        this.mTitle = mTitle;
        this.mValue = mValue;
    }

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
