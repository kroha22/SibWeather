package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Wind implements Serializable {

    @SerializedName("speed")
    private Property speed;

    @SerializedName("direction")
    private Direction direction;

    public Wind() {/**/}

    public Wind(Property speed, Direction direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public Property getSpeed() {
        return speed;
    }

    public void setSpeed(Property speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
