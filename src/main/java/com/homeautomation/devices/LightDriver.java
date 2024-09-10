package com.homeautomation.devices;

import com.homeautomation.LightController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LightDriver {
    int id;
    // X10
    int unit;
    String message;
    X10HouseCode house;
    // Acme Wireless
    private String ssid;
    private String key;
    private int channel;
    // Memory Mapped
    private List<Integer> addresses = new ArrayList<>();

    public LightDriver(int id) { this.id = id; }

    // for testing purpose
    private static final int[] states = new int[LightController.MAX_LIGHTS];
    private static int lastId = LightId.UNKNOWN.getValue();
    private static int lastState = LightState.UNKNOWN.getValue();

    public static void reset() {
        Arrays.fill(states, LightState.UNKNOWN.getValue());
        lastId = LightId.UNKNOWN.getValue();
        lastState = LightState.UNKNOWN.getValue();
    }

    private static void update(int id, int state) {
        states[id] = state;
        lastId = id;
        lastState = state;
    }

    public static int getState(int id) {
        if (id >= 0 && id < LightController.MAX_LIGHTS) {
            return states[id];
        }
        return LightState.UNKNOWN.getValue();
    }

    public static int getLastId() {
        return lastId;
    }

    public static int getLastState() {
        return lastState;
    }

    public void X10Create(int unit, String message, X10HouseCode house) {
        this.unit = unit;
        this.message = message;
        this.house = house;
    }
    public void X10TurnOn() {
        // Implement LED light turning on logic
    }

    public void X10TurnOff() {
        // Implement LED light turning off logic
    }

    public void X10Destroy() {
        // Implement LED light destruction logic
    }

    public void AcmeWirelessCreate(String ssid, String key, int channel) {
        this.ssid = ssid;
        this.key = key;
        this.channel = channel;
    }

    public void AcmeWirelessTurnOn() {
        // Implement LED light turning on logic
    }

    public void AcmeWirelessTurnOff() {
        // Implement LED light turning off logic
    }

    public void AcmeWirelessDestroy() {
        // Implement LED light destruction logic
    }

    public void MemoryMappedCreate(List<Integer> addresses) {
        this.addresses = addresses;
    }

    public void MemoryMappedTurnOn() {
        // Implement LED light turning on logic
    }

    public void MemoryMappedTurnOff() {
        // Implement LED light turning off logic
    }

    public void MemoryMappedDestroy() {
        // Implement LED light destruction logic
    }

    public void SpyTurnOn() {
        update(id, LightState.ON.getValue());
    }

    public void SpyTurnOff() {
        update(id, LightState.OFF.getValue());
    }

    public void SpyDestroy() {
        // Implement Spy destruction logic
    }
}