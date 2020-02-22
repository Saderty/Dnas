package com.saderty.Functions;

public class ReservNotHot {
    public static double P(double l0, double l1, double t, double m) {
        double d = 0;
        for (int i = 1; i <= m; i++) {
            d += (a(i, l0, l1) / factorial(i)) * Math.pow(1 - Math.exp(-l1 * t), i);
        }
        d++;
        d *= Math.exp(-l0 * t);

        return d;
    }

    public static double P(double l0, double t, double m) {
        double d = 0;
        for (int i = 0; i <= m; i++) {
            d += Math.pow(l0 * t, i) / factorial(i);
        }
        d *= Math.exp(-l0 * t);
        return d;
    }

    public static double Q(double l0, double l1, double t, double m) {
        return 1 - P(l0, l1, t, m);
    }

    public static double Q(double l0, double t, double m) {
        return 1 - P(l0, t, m);
    }

    public static double M(double l0, double l1, double m) {
        double d = 0;
        for (int i = 0; i <= m; i++) {
            d += 1 / (1 + i * (l1 / l0));
        }
        d *= 1 / l0;
        return d;
    }

    public static double M(double l0, double m) {
        return (m + 1) / l0;
    }

    public static double F(double l0, double l1, double t, double m) {
        double d0 = 0, d1 = 0;
        for (int i = 1; i <= m; i++) {
            d0 += a(i, l0, l1) / factorial(i) * Math.pow(1 - Math.exp(-l1 * t), i);
            d1 += a(i, l0, l1) / factorial(i - 1) * Math.pow(1 - Math.exp(-l1 * t), i - 1);
        }
        d0 += 1 - (l1 / l0 * Math.exp(-l1 * t) * d1);
        d0 *= l0 * Math.exp(-l0 * t);
        return d0;
    }

    public static double F(double l0, double t, double m) {
        return Math.pow(l0, m + 1) / factorial((int) m) * Math.pow(t, m) * Math.exp(-l0 * t);
    }

    public static double L(double l0, double l1, double t, double m) {
        return F(l0, l1, t, m) / P(l0, l1, t, m);
    }

    public static double L(double l0, double t, double m) {
        return F(l0, t, m) / P(l0, t, m);
    }

    static double a(int i, double l0, double l1) {
        double d = 1;
        for (int j = 0; j <= i - 1; j++) {
            d *= j + l0 / l1;
        }
        return d;
    }

    static double factorial(int f) {
        double factorial = 1;
        for (int i = 1; i <= f; i++) {
            factorial *= i;
        }
        return factorial;
    }

    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
}
