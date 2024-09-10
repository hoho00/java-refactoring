package com.homeautomation;

import com.homeautomation.devices.LightDriver;
import com.homeautomation.devices.LightDriverStruct;
import com.homeautomation.devices.LightState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.homeautomation.devices.LightDriverType.*;
import static org.junit.jupiter.api.Assertions.*;

public class LightControllerTest {
    private LightController lightController;

    @BeforeEach
    public void setUp() {
        lightController = new LightController();
        LightDriverStruct.addSpiesToController(lightController);
        LightDriver.reset();
    }

    @AfterEach
    public void tearDown() {
        lightController.destroy();
    }

    @Test
    public void createDestroy() {
        // No specific actions required
    }
    @Test
    public void driverIsDestroyedByLightController() {
        LightDriverStruct spy = new LightDriverStruct(SPY, 1, new LightDriver(1));
        lightController.add(1, spy);
    }

    @Test
    public void turnOn() {
        lightController.turnOn(7);
        assertEquals(LightState.ON.getValue(), LightDriver.getState(7));
    }

    @Test
    public void turnOff() {
        lightController.turnOff(1);
        assertEquals(LightState.OFF.getValue(), LightDriver.getState(1));
    }

    @Test
    public void allDriversDestroyed() {
        for (int i = 0; i < LightController.MAX_LIGHTS; i++) {
            LightDriverStruct spy = new LightDriverStruct(SPY, i, new LightDriver(i));
            assertTrue(lightController.add(i, spy));
        }
    }

    @Test
    public void validIdLowerRange() {
        LightDriverStruct spy = new LightDriverStruct(SPY, 0, new LightDriver(0));
        assertTrue(lightController.add(0, spy));
    }

    @Test
    public void validIdUpperRange() {
        LightDriverStruct spy = new LightDriverStruct(SPY, LightController.MAX_LIGHTS - 1, new LightDriver(LightController.MAX_LIGHTS - 1));
        assertTrue(lightController.add(LightController.MAX_LIGHTS - 1, spy));
    }

    @Test
    public void inValidIdBeyondUpperRange() {
        LightDriverStruct spy = new LightDriverStruct(SPY, LightController.MAX_LIGHTS, new LightDriver(LightController.MAX_LIGHTS));
        assertFalse(lightController.add(LightController.MAX_LIGHTS, spy));
    }

    @Test
    public void removeExistingLightDriverSucceeds() {
        assertTrue(lightController.remove(10));
    }

    @Test
    public void removedLightDoesNothing() {
        lightController.remove(1);
        lightController.turnOn(1);
        assertEquals(LightState.UNKNOWN.getValue(), LightDriver.getState(1));
        lightController.turnOff(1);
        assertEquals(LightState.UNKNOWN.getValue(), LightDriver.getState(1));
    }

    @Test
    public void rejectsNullDrivers() {
        assertFalse(lightController.add(1, null));
    }

    @Test
    public void removeNonExistingLightDriverFails() {
        assertTrue(lightController.remove(10));
        assertFalse(lightController.remove(10));
    }
}
