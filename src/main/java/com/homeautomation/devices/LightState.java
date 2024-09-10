package com.homeautomation.devices;

public enum LightState {
    UNKNOWN(-1),
    OFF(0),
    ON(1);

    private final int value;

    LightState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
