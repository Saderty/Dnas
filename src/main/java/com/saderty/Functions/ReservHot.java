package com.saderty.Functions;

public class ReservHot {
    public static double Q(double[] p, double m) {
        double d = 1;
        for (int i = 0; i < m; i++)
            d *= 1 - Basic.mult(p);
        return d;
    }

    public static double P(double[] p, double m) {
        return 1 - Q(p, m);
    }
}
