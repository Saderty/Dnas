package com.saderty.Functions;

public class Basic {
    public static double sum(double[] nums) {
        double d = 0;
        for (double num : nums) d += num;
        return d;
    }

    public static double mult(double[] nums) {
        double d = 1;
        for (double num : nums) d *= num;
        return d;
    }
}
