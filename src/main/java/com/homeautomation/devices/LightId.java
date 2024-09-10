package com.homeautomation.devices;

public enum LightId {
    UNKNOWN(-1);

    private final int value;

    LightId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
