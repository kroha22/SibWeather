package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Resultset implements Serializable {

    @SerializedName("count")
    private int mCount;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
