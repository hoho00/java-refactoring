package com.homeautomation.time;

public class Time {
    private int usec;
    private int sec;
    private int minuteOfDay;
    private int minuteOfHour;
    private int dayOfWeek;
    private int dayOfMonth;
    private Month month;

    public Time(int minuteOfDay, int dayOfWeek) {
        this.minuteOfDay = minuteOfDay;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean matchesDayOfWeek(Day day) {
        int today = dayOfWeek;

        if (day == Day.EVERYDAY) {
            return true;
        }
        if (day == Day.WEEKEND && (today == Day.SATURDAY.getValue() || today == Day.SUNDAY.getValue())) {
            return true;
        }
        if (day == Day.WEEKDAY && today >= Day.MONDAY.getValue() && today <= Day.FRIDAY.getValue()) {
            return true;
        }
        return day.getValue() == today;
    }

    public boolean matchesMinuteOfDay(int minute) {
        return minuteOfDay == minute;
    }
}
