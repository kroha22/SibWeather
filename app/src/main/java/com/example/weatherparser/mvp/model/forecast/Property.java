package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Property implements Serializable {

    @SerializedName("min")
    private int mMin;

    @SerializedName("max")
    private int mMax;

    @SerializedName("avg")
    private int mAvg;

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        mMin = min;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getAvg() {
        return mAvg;
    }

    public void setAvg(int avg) {
        mAvg = avg;
    }
}
