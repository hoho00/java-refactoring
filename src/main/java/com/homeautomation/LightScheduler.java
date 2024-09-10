package com.homeautomation;

import com.homeautomation.time.Day;
import com.homeautomation.time.Time;
import com.homeautomation.devices.TimeService;

public class LightScheduler {
    private static final int MAX_EVENTS = 100; // Adjust as needed
    private final ScheduledLightEvent[] eventList = new ScheduledLightEvent[MAX_EVENTS];
    private final LightController lightController;
    private final TimeService timeService;

    public LightScheduler(TimeService timeService, LightController lightController) {
        this.timeService = timeService;
        this.lightController = lightController;
    }
    public void create() {
        for (int i = 0; i < MAX_EVENTS; i++) {
            eventList[i] = new ScheduledLightEvent(ScheduledLightEvent.UNUSED, Day.EVERYDAY, 0, LightControlEvent.TURN_OFF, 0);
        }
    }

    public void destroy() {
        // No specific actions required for destruction
    }

    private ScheduledLightEvent findUnusedEvent() {
        for (ScheduledLightEvent event : eventList) {
            if (!event.isInUse()) {
                return event;
            }
        }
        return null;
    }

    public void scheduleEvent(int id, Day day, int minute, LightControlEvent event, int randomize) {
        ScheduledLightEvent scheduledEvent = findUnusedEvent();
        if (scheduledEvent != null) {
            scheduledEvent.id = id;
            scheduledEvent.day = day;
            scheduledEvent.minuteOfDay = minute;
            scheduledEvent.event = event;
            scheduledEvent.randomize = randomize;
            scheduledEvent.resetRandomize();
        }
    }

    public void scheduleTurnOn(int id, Day day, int minute) {
        scheduleEvent(id, day, minute, LightControlEvent.TURN_ON, LightControlEvent.RANDOM_OFF.ordinal());
    }

    public void scheduleTurnOff(int id, Day day, int minute) {
        scheduleEvent(id, day, minute, LightControlEvent.TURN_OFF, LightControlEvent.RANDOM_OFF.ordinal());
    }

    public void randomize(int id, Day day, int minute) {
        for (ScheduledLightEvent event : eventList) {
            if (event.matchEvent(id, day, minute)) {
                event.randomize = LightControlEvent.RANDOM_ON.ordinal();
                event.resetRandomize();
                break; // Assuming only one match is expected
            }
        }
    }

    public void scheduleRemove(int id, Day day, int minute) {
        for (ScheduledLightEvent event : eventList) {
            if (event.matchEvent(id, day, minute)) {
                event.id = ScheduledLightEvent.UNUSED;
                break; // Assuming only one match is expected
            }
        }
    }

    private void operateLight(ScheduledLightEvent event) {
        if (event.event == LightControlEvent.TURN_ON) {
            lightController.turnOn(event.id);
        } else if (event.event == LightControlEvent.TURN_OFF) {
            lightController.turnOff(event.id);
        }
    }

    public void wakeUp() {
        Time time = timeService.getTime();

        for (ScheduledLightEvent event : eventList) {
            processEventsDueNow(time, event);
        }
    }

    private void processEventsDueNow(Time time, ScheduledLightEvent event) {
        if (event.isInUse()) {
            if (isEventDueNow(time, event)) {
                operateLight(event);
                event.resetRandomize();
            }
        }
    }

    private boolean isEventDueNow(Time time, ScheduledLightEvent event) {
        int todaysMinute = event.minuteOfDay + event.randomMinutes;
        Day day = event.day;

        return time.matchesMinuteOfDay(todaysMinute) && time.matchesDayOfWeek(day);
    }

}
