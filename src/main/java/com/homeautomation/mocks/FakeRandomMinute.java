package com.homeautomation.mocks;

public class FakeRandomMinute {
    private int seed = -1;
    private int increment = -1;

    public void reset() {
        seed = -1;
        increment = -1;
    }

    public void setFirstAndIncrement(int s, int i) {
        seed = s;
        increment = i;
    }

    public int get() {
        int result = seed;
        seed += increment;
        return result;
    }
}