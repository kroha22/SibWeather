package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Property implements Serializable {

    @SerializedName("min")
    private int mMin;

    @SerializedName("max")
    private int mMax;

    @SerializedName("avg")
    private int mAvg;

    public Property() {/**/}

    public Property(int mMin, int mMax, int mAvg) {
        this.mMin = mMin;
        this.mMax = mMax;
        this.mAvg = mAvg;
    }

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
