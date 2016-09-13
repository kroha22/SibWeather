package com.example.weatherparser.mvp.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {

    @SerializedName("speed")
    private Property mSpeed;

    @SerializedName("direction")
    private Direction mDirection;

    public Property getSpeed() {
        return mSpeed;
    }

    public void setSpeed(Property speed) {
        mSpeed = speed;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
    }
}
