package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Property implements Serializable {

    @SerializedName("min")
    private int min;

    @SerializedName("max")
    private int max;

    @SerializedName("avg")
    private int avg;

    public Property() {/**/}

    public Property(int min, int max, int avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }
}
