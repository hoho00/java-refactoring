package com.homeautomation;
import java.util.Random;

public class RandomMinute {
    private int bound;
    private final Random random = new Random();

    public void create(int b) {
        bound = b;
    }

    public int get() {
        return bound - random.nextInt(bound * 2 + 1);
    }
}
