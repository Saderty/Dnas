package com.saderty.Functions;

public class ReservHotExp {
    public static double P(double l, double t) {
        return Math.exp(-l * t);
    }

    public static double Q(double l, double t, double m) {
        return Math.pow(1 - P(l, t), m + 1);
    }

    public static double P(double l, double t, double m) {
        return 1 - Q(l, t, m);
    }

    public static double M(double l, double m) {
        double d = 0;
        for (int i = 0; i <= m; i++) {
            d += 1 / (1 + (double)i);
        }
        return 1 / l * d;
    }

    public static double F(double l, double t, double m) {
        return l * (m + 1) * P(l, t) * Math.pow(1 - P(l, t), m);
    }

    public static double L(double l, double t, double m) {
        return F(l, t, m) / P(l, t, m);
    }
}
