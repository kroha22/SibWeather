package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Forecast implements Serializable {

    @SerializedName("date")
    private String mDate;

    @SerializedName("hours")
    private List<Hour> mHours;

    @SerializedName("astronomy")
    private Atmosphere mAstronomy;

    @SerializedName("links")
    private Links mLinks;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<Hour> getHours() {
        return mHours;
    }

    public void setHours(List<Hour> hours) {
        mHours = hours;
    }

    public Atmosphere getAstronomy() {
        return mAstronomy;
    }

    public void setAstronomy(Atmosphere astronomy) {
        mAstronomy = astronomy;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        mLinks = links;
    }
}
