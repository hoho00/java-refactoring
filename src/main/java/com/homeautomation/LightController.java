package com.homeautomation;

import java.util.Arrays;

import com.homeautomation.devices.LightDriverStruct;

public class LightController {
    static public final int MAX_LIGHTS = 100; // Adjust as needed
    private final LightDriverStruct[] lightDriverStructs = new LightDriverStruct[MAX_LIGHTS];

    public void create() {
        Arrays.fill(lightDriverStructs, null);
    }

    public void destroyDriver(LightDriverStruct lightDriverStruct) {
        if (lightDriverStruct == null) {
            return;
        }
        switch (lightDriverStruct.getType()) {
            case X10:
                lightDriverStruct.getLightDriver().X10Destroy();
                break;
            case ACME_WIRELESS:
                lightDriverStruct.getLightDriver().AcmeWirelessDestroy();
                break;
            case MEMORY_MAPPED:
                lightDriverStruct.getLightDriver().MemoryMappedDestroy();
                break;
            case SPY:
                lightDriverStruct.getLightDriver().SpyDestroy();
                break;
        }
    }

    public void destroy() {
        for (LightDriverStruct driver : lightDriverStructs) {
            if (driver != null) {
                destroyDriver(driver);
            }
        }
    }

    private boolean isIdInBounds(int id) {
        return id < 0 || id >= MAX_LIGHTS;
    }

    public boolean add(int id, LightDriverStruct lightDriverStruct) {
        if (isIdInBounds(id) || lightDriverStruct == null) {
            return false;
        }

        if (lightDriverStructs[id] != null) {
            destroyDriver(lightDriverStructs[id]);
        }

        lightDriverStructs[id] = lightDriverStruct;
        return true;
    }

    public boolean remove(int id) {
        if (isIdInBounds(id) || lightDriverStructs[id] == null) {
            return false;
        }

        destroyDriver(lightDriverStructs[id]);
        lightDriverStructs[id] = null;
        return true;
    }

    public void turnOn(int id) {
        if (isIdInBounds(id) || lightDriverStructs[id] == null) {
            return;
        }
        switch (lightDriverStructs[id].getType()) {
            case X10:
                lightDriverStructs[id].getLightDriver().X10TurnOn();
                break;
            case ACME_WIRELESS:
                lightDriverStructs[id].getLightDriver().AcmeWirelessTurnOn();
                break;
            case MEMORY_MAPPED:
                lightDriverStructs[id].getLightDriver().MemoryMappedTurnOn();
                break;
            case SPY:
                lightDriverStructs[id].getLightDriver().SpyTurnOn();
                break;
        }
    }

    public void turnOff(int id) {
        if (isIdInBounds(id) || lightDriverStructs[id] == null) {
            return;
        }
        switch (lightDriverStructs[id].getType()) {
            case X10:
                lightDriverStructs[id].getLightDriver().X10TurnOff();
                break;
            case ACME_WIRELESS:
                lightDriverStructs[id].getLightDriver().AcmeWirelessTurnOff();
                break;
            case MEMORY_MAPPED:
                lightDriverStructs[id].getLightDriver().MemoryMappedTurnOff();
                break;
            case SPY:
                lightDriverStructs[id].getLightDriver().SpyTurnOff();
                break;
        }
    }
}
