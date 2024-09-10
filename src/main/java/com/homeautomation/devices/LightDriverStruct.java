package com.homeautomation.devices;

import com.homeautomation.LightController;

import static com.homeautomation.devices.LightDriverType.*;

public class LightDriverStruct {
    private final LightDriverType type;
    private final int id;
    private final LightDriver lightDriver;

    // for testing purpose
    public static void addSpiesToController(LightController controller) {
        for (int i = 0; i < LightController.MAX_LIGHTS; i++) {
            LightDriverStruct spy = new LightDriverStruct(SPY, i, new LightDriver(i));
            controller.add(i, spy);
        }
    }

    public LightDriverStruct(LightDriverType type, int id, LightDriver lightDriver) {
        this.type = type;
        this.id = id;
        this.lightDriver = lightDriver;
    }

    public LightDriverType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LightDriver getLightDriver() {
        return lightDriver;
    }
}