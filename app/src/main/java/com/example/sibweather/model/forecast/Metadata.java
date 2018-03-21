package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Metadata {

    @SerializedName("resultset")
    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
