package com.saderty.Functions;

public class ReservExp {
    public static double L(double[] n) {
        return Basic.sum(n);
    }

    public static double L_i(double m) {
        return 1 / m;
    }

    public static double P(double l, double t) {
        return Math.exp(-l * t);
    }

    public static double Q(double l, double t) {
        return 1 - P(l, t);
    }

    public static double F(double l, double t) {
        return l * P(l, t);
    }

    public static double M(double lc) {
        return 1 / lc;
    }

    public static double M(double[] n) {
        return 1 / L(n);
    }
}
