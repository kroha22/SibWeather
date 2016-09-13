package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Direction extends Atmosphere implements Serializable {

    @SerializedName("title_letter")
    private String mTitleLetter;

    @SerializedName("title_short")
    private String mTitleShort;

    public String getTitleLetter() {
        return mTitleLetter;
    }

    public void setTitleLetter(String titleLetter) {
        mTitleLetter = titleLetter;
    }

    public String getTitleShort() {
        return mTitleShort;
    }

    public void setTitleShort(String titleShort) {
        mTitleShort = titleShort;
    }
}
