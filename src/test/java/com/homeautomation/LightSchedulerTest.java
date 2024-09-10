package com.homeautomation;
import com.homeautomation.devices.LightDriver;
import com.homeautomation.devices.LightDriverStruct;
import com.homeautomation.mocks.FakeTimeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.homeautomation.mocks.*;
import com.homeautomation.time.Day;
import com.homeautomation.devices.LightId;
import com.homeautomation.devices.LightState;

public class LightSchedulerTest {
    private final LightController lightController = new LightController();
    private final FakeTimeService fakeTimeService = new FakeTimeService();
    private final LightScheduler lightScheduler = new LightScheduler(fakeTimeService, lightController);
    private final FakeRandomMinute fakeRandomMinute = new FakeRandomMinute();
    private int scheduledMinute;
    private int expectedId;
    private int expectedLevel;
    private int lightNumber;
    private int seed;
    private int pseudoRandomIncrement;

    @BeforeEach
    public void setUp() {
        LightDriver.reset();
        lightController.create();
        LightDriverStruct.addSpiesToController(lightController);
        lightScheduler.create();

        scheduledMinute = 1234;
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
        lightNumber = 4;

        fakeRandomMinute.reset();
        seed = -10;
        pseudoRandomIncrement = 5;
    }

    @AfterEach
    public void tearDown() {
        lightScheduler.destroy();
        lightController.destroy();
    }

    private void checkLightState(int id, LightState level) {
        if (id == LightId.UNKNOWN.getValue()) {
            assertEquals(LightState.UNKNOWN.getValue(), LightDriver.getLastState());
        } else {
            assertEquals(level.getValue(), LightDriver.getState(id));
        }
    }

    private void setTimeTo(Day day, int minute) {
        fakeTimeService.setDay(day.getValue());
        fakeTimeService.setMinute(minute);
    }

    @Test
    public void createDoesNotChangeTheLights() {
        assertEquals(LightId.UNKNOWN.getValue(), LightDriver.getLastId());
        assertEquals(LightState.UNKNOWN.getValue(), LightDriver.getLastState());
    }

    @Test
    public void scheduleEverydayNotTimeYet() {
        lightScheduler.scheduleTurnOn(3, Day.EVERYDAY, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute - 1);
        lightScheduler.wakeUp();
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
    }

    @Test
    public void scheduleOnTodayItsTime() {
        lightScheduler.scheduleTurnOn(3, Day.EVERYDAY, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(3, LightState.ON);
    }

    @Test
    public void scheduleOnTuesdayAndItsNotTuesdayAndItsTime() {
        lightScheduler.scheduleTurnOn(3, Day.TUESDAY, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
    }

    @Test
    public void scheduleOnTuesdayAndItsTuesdayAndItsTime() {
        lightScheduler.scheduleTurnOn(3, Day.TUESDAY, scheduledMinute);
        setTimeTo(Day.TUESDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(3, LightState.ON);
    }

    @Test
    public void scheduleOffTuesdayAndItsTuesdayAndItsTime() {
        lightScheduler.scheduleTurnOff(lightNumber, Day.TUESDAY, scheduledMinute);
        setTimeTo(Day.TUESDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.OFF);
    }

    @Test
    public void scheduleOffWeekendAndItsSaturdayAndItsTime() {
        lightScheduler.scheduleTurnOff(lightNumber, Day.WEEKEND, scheduledMinute);
        setTimeTo(Day.SATURDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.OFF);
    }

    @Test
    public void scheduleOnWeekendAndItsSundayAndItsTime() {
        lightScheduler.scheduleTurnOn(lightNumber, Day.WEEKEND, scheduledMinute);
        setTimeTo(Day.SUNDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.ON);
    }

    @Test
    public void scheduleOnWeekendAndItsMondayAndItsTime() {
        lightScheduler.scheduleTurnOff(lightNumber, Day.WEEKEND, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute);
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
    }

    @Test
    public void scheduleOnWeekdayAndItsSundayAndItsTime() {
        lightScheduler.scheduleTurnOn(lightNumber, Day.WEEKEND, scheduledMinute);
        setTimeTo(Day.SUNDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.ON);
    }

    @Test
    public void scheduleOnWeekdayAndItsMondayAndItsTime() {
        lightScheduler.scheduleTurnOn(lightNumber, Day.WEEKDAY, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.ON);
    }

    @Test
    public void scheduleOnWeekdayAndItsFridayAndItsTime() {
        lightScheduler.scheduleTurnOn(lightNumber, Day.WEEKDAY, scheduledMinute);
        setTimeTo(Day.FRIDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(lightNumber, LightState.ON);
    }

    @Test
    public void scheduleOnWeekdayAndItsSaturdayAndItsTime() {
        lightScheduler.scheduleTurnOn(lightNumber, Day.WEEKDAY, scheduledMinute);
        setTimeTo(Day.SATURDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
    }

    @Test
    public void removeScheduledEvent() {
        lightScheduler.scheduleTurnOn(6, Day.MONDAY, scheduledMinute);
        lightScheduler.scheduleRemove(6, Day.MONDAY, scheduledMinute);
        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();
        checkLightState(LightId.UNKNOWN.getValue(), LightState.UNKNOWN);
    }

    @Test
    public void multipleScheduledEventsSameTime() {
        lightScheduler.scheduleTurnOff(4, Day.MONDAY, scheduledMinute);
        lightScheduler.scheduleTurnOn(3, Day.MONDAY, scheduledMinute);

        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();

        checkLightState(4, LightState.OFF);
        checkLightState(3, LightState.ON);
    }

    @Test
    public void multipleScheduledEventsDifferentTimes() {
        lightScheduler.scheduleTurnOff(4, Day.MONDAY, scheduledMinute);
        lightScheduler.scheduleTurnOn(3, Day.MONDAY, scheduledMinute + 1);

        setTimeTo(Day.MONDAY, scheduledMinute);
        lightScheduler.wakeUp();

        checkLightState(4, LightState.OFF);

        setTimeTo(Day.MONDAY, scheduledMinute + 1);
        lightScheduler.wakeUp();

        checkLightState(3, LightState.ON);
    }
}
