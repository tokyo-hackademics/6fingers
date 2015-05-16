package com.fingers.six.elarm.utils;

public class RandomUtils {
    private RandomUtils() {}

    public static int next(int upperbound) {
        return (int)(Math.random() * upperbound);
    }
}
