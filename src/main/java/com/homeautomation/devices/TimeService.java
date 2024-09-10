package com.homeautomation.devices;

import com.homeautomation.time.Day;
import com.homeautomation.time.Time;

public class TimeService {
    public void create() {
        // ...
    }

    public void destroy() {
        // ...
    }

    public int getMinute() {
        // ...
        return 0;
    }

    public int getDay() {
        // ...
        return 0;
    }

    public Time getTime() {
        // ...
        return null;
    }

    public boolean matchesNow(int reactionDay, int minute) {
        if (getMinute() != minute) {
            return false;
        }

        int today = getDay();
        if (reactionDay == Day.EVERYDAY.getValue()) {
            return true;
        }
        if (reactionDay == today) {
            return true;
        }
        if (reactionDay == Day.WEEKEND.getValue() && (today == Day.SATURDAY.getValue() || today == Day.SUNDAY.getValue())) {
            return true;
        }
        if (reactionDay == Day.WEEKDAY.getValue() && today >= Day.MONDAY.getValue() && today <= Day.FRIDAY.getValue()) {
            return true;
        }
        return false;
    }
}
