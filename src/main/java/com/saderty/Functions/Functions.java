package com.saderty.Functions;

public class Functions {
    static double Ps(double n, double nt) {
        return (n - nt) / n;
    }

    static double Qs(double n, double nt) {
        return nt / n;
    }

    static double Fs(double ndt, double n, double dt) {
        return ndt / (n * dt);
    }

    static double Ms(String[] times) {
        double[] timeds = new double[times.length];
        for (int i = 0; i < times.length; i++) {
            timeds[i] = Double.parseDouble(times[i]);
        }
        return Ms(timeds);
    }

    static double Ms(double[] times) {
        double tmp = 0;
        for (double time : times) {
            tmp += time;
        }
        tmp /= times.length;
        return tmp;
    }

    static double P(double t, double l) {
        return Math.exp(-l * t);
    }

    //L
    static double Pl(double t, double m, double a) {
        return 0.5 - Applications.Laplace(Ul(t, m, a));
    }

    //R
    static double Pr(double t, double a) {
        return Math.exp(-(t * t) / (2 * a * a));
    }

    //V
    static double Pv(double k, double a, double t) {
        return Math.exp(-a * Math.pow(t, k));
    }

    static double Q(double t, double l) {
        return 1 - P(t, l);
    }

    static double F(double t, double l) {
        return l * P(t, l);
    }

    //L
    static double Fl(double t, double m, double a) {
        return Applications.Reley(Ul(t, m, a)) / a;
    }

    //R
    static double Fr(double t, double a) {
        return (t * Pr(t, a)) / (a * a);
    }

    //V
    static double Fv(double k, double a, double t) {
        return Lv(k, a, t) * Pv(k, a, t);
    }

    static double M(double l) {
        return 1 / l;
    }

    //R
    static double Mr(double a) {
        return Math.sqrt(Math.PI / 2) * a;
    }

    //V
    static double Mv(double k,double a){
        return (1/k*Applications.Vebyal(1/k))/(Math.pow(a,1/k));
    }

    //L
    static double Ul(double t, double m, double a) {
        return (t - m) / a;
    }

    //L
    static double Ll(double t, double m, double a) {
        return Fl(t, m, a) / Pl(t, m, a);
    }

    //R
    static double Lr(double t, double a) {
        return t / (a * a);
    }

    //V
    static double Lv(double k, double a, double t) {
        return a * k * Math.pow(t, k - 1);
    }

    public static void main(String[] args) {
    }
}
