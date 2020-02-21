package com.saderty.Functions;

public class Basic {
    public static double sum(double[] nums) {
        double d = 0;
        for (double num : nums) d += num;
        return d;
    }

    public static double mult(double[] nums) {
        double d = 0;
        for (double num : nums) d *= num;
        return d;
    }

    public static String trimBorders(String s) {
        return s.substring(1, s.length() - 1);
    }

    public static double[] toArray(String s) {
        s = trimBorders(s);
        String[] strings = s.split(",");
        double[] doubles = new double[strings.length];
        for (int i = 0; i < strings.length; i++)
            doubles[i] = Double.parseDouble(strings[i]);
        return doubles;
    }
}
