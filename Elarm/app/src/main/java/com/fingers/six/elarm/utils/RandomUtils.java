package com.fingers.six.elarm.utils;

public class RandomUtils {
    private RandomUtils() {}

    public static int nextInt(int upperbound) {
        return (int)(Math.random() * upperbound);
    }
}
