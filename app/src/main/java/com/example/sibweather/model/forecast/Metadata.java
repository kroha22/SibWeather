package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("resultset")
    private ResultSet mResultset;

    public ResultSet getResultset() {
        return mResultset;
    }

    public void setResultset(ResultSet resultset) {
        mResultset = resultset;
    }
}
