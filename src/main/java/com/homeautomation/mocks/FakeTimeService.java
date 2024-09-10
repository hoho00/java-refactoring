package com.homeautomation.mocks;

import com.homeautomation.devices.TimeService;
import com.homeautomation.time.Time;

public class FakeTimeService extends TimeService {
    private int theMinute = TimeConstants.MINUTE_UNKNOWN.getValue();
    private int theDay = TimeConstants.DAY_UNKNOWN.getValue();

    public void setMinute(int minute) {
        theMinute = minute;
    }

    public void setDay(int day) {
        theDay = day;
    }

    public int getMinute() {
        return theMinute;
    }

    public int getDay() {
        return theDay;
    }

    public Time getTime() {
        return new Time(theMinute, theDay);
    }
}
