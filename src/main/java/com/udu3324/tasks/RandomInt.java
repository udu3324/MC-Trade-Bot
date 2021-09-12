package com.udu3324.tasks;

public class RandomInt {
    public static int get(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
