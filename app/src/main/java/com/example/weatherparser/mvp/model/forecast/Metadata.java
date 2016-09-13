package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("resultset")
    private Resultset mResultset;

    public Resultset getResultset() {
        return mResultset;
    }

    public void setResultset(Resultset resultset) {
        mResultset = resultset;
    }
}
