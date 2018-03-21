package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Direction extends Atmosphere implements Serializable {

    @SerializedName("title_letter")
    private String titleLetter;

    @SerializedName("title_short")
    private String titleShort;

    public Direction() {/**/}

    public Direction(String title, String val, String titleLetter, String titleShort) {
        super(title, val);

        this.titleLetter = titleLetter;
        this.titleShort = titleShort;
    }

    public String getTitleLetter() {
        return titleLetter;
    }

    public void setTitleLetter(String titleLetter) {
        this.titleLetter = titleLetter;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }
}
