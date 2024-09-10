package com.homeautomation.mocks;

public enum TimeConstants {
    MINUTE_UNKNOWN(-1),
    DAY_UNKNOWN(-1);

    private final int value;

    TimeConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}