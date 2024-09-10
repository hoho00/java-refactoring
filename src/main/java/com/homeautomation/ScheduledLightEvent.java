package com.homeautomation;

import com.homeautomation.time.Day;

import java.util.Objects;

public class ScheduledLightEvent {
    public static final int UNUSED = -1;
    int id;
    Day day;
    int minuteOfDay;
    LightControlEvent event;
    RandomMinute randomMinute = new RandomMinute();
    int randomize;
    int randomMinutes;

    public ScheduledLightEvent(int id, Day day, int minute, LightControlEvent event, int randomize) {
        this.id = id;
        this.day = day;
        this.minuteOfDay = minute;
        this.event = event;
        this.randomize = randomize;
        resetRandomize();
    }

    public boolean isInUse() {
        return id != UNUSED;
    }

    public boolean matchEvent(int id, Day day, int minute) {
        return this.id == id && this.day == day && this.minuteOfDay == minute;
    }

    public void resetRandomize() {
        if (randomize == LightControlEvent.RANDOM_ON.ordinal()) {
            randomMinutes = randomMinute.get();
        } else {
            randomMinutes = 0;
        }
    }
}
