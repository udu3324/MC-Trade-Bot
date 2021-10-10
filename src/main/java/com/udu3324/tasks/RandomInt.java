package com.udu3324.tasks;

public class RandomInt {
    // Class Info - .get returns a random number between min and max using Math.random()
    public static int get(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
