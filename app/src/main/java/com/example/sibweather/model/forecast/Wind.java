package com.example.sibweather.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {

    @SerializedName("speed")
    private Property mSpeed;

    @SerializedName("direction")
    private Direction mDirection;

    public Wind() {/**/}

    public Wind(Property mSpeed, Direction mDirection) {
        this.mSpeed = mSpeed;
        this.mDirection = mDirection;
    }

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

    @Override
    public String toString() {
        return mSpeed.getMin() + "-" + mSpeed.getMax() + " " + mDirection.getTitleShort();
    }
}
