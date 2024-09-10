package com.homeautomation.time;

public enum Day {
    EVERYDAY(-3), WEEKDAY(-2), WEEKEND(-1),
    SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);

    private final int value;

    Day(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
