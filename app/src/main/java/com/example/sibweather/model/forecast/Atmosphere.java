package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Atmosphere implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("value")
    private String value;

    public Atmosphere() {/**/}

    public Atmosphere(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
