package com.saderty.Functions;

import javax.swing.*;
import java.util.*;

import static com.saderty.Dnas.lambda;
import static com.saderty.Dnas.sigma;

public class Base {
    private Map<String, String> map = new HashMap<>();
    private List<Integer> times = new LinkedList<>();

    public Base(JTextArea area) {
        String[] numLines = area.getText().split("\n");
        for (String numLine : numLines) {
            String[] tmp = numLine.replaceAll(" ", "").split("=");
            if (tmp.length == 1) continue;
            if (tmp[0].contains("("))
                if (!tmp[0].substring(tmp[0].indexOf("(") + 1, tmp[0].indexOf(")")).equals("t"))
                    times.add(Integer.valueOf(tmp[0].substring(tmp[0].indexOf("(") + 1, tmp[0].indexOf(")"))));
            map.put(tmp[0], tmp[1]);
        }
    }

    public void processFunctions1(JTextArea area) {
        StringBuilder s = new StringBuilder();
        for (int time : times) {
            if (contains("N", "n(" + time + ")")) {
                double n = get("N");
                double nt = get("n(" + time + ")");
                s.append(toOut("P(" + time + ")", Functions.Ps(n, nt)));
                s.append(toOut("q(" + time + ")", Functions.Qs(n, nt)));
            }
        }
        if (times.size() == 2) {
            double n = get("N");
            double nt1 = get("n(" + times.get(1) + ")");
            double nt0 = get("n(" + times.get(0) + ")");
            double ns = n - (nt0 + nt1) / 2;
            s.append(toOut("f(" + times.get(0) + ")", Functions.Fs((int) (nt1 - nt0), n, times.get(1) - times.get(0))));
            s.append(toOut(lambda + "(" + times.get(0) + ")", Functions.Fs((int) (nt1 - nt0), ns, times.get(1) - times.get(0))));
        }
        if (contains("t[]")) {
            String[] ts = map.get("t[]").split(",");
            s.append(toOut("m", Functions.Ms(ts)));
        }
        if (contains("n(t)[]", "t")) {
            String[] ts = map.get("n(t)[]").split(",");
            double t = Double.parseDouble(map.get("t"));
            double interval = t / ts.length / 2;

            double sum = 0;
            for (int i = 0; i < ts.length; i++) {
                sum += (interval + (interval * i * 2)) * Double.parseDouble(ts[i]);
            }

            double n = 0;
            for (String value : ts) {
                n += Double.parseDouble(value);
            }

            sum /= n;

            s.append(toOut("m", sum));
        }

        area.setText(s.toString());
    }

    public void processFunctions2(JTextArea area) {
        StringBuilder s = new StringBuilder();

        if (contains(lambda, "t")) {
            double t = get("t");
            double l = get(lambda);

            s.append(toOut("P(t)", Functions.P(t, l)));
            s.append(toOut("q(t)", Functions.Q(t, l)));
            s.append(toOut("f(t)", Functions.F(t, l)));
            s.append(toOut("m(t)", Functions.M(l)));
        } else if (contains("m", sigma, "t")) {
            double m = get("m");
            double a = get(sigma);
            double t = get("t");

            s.append(toOut("P(t)", Functions.Pl(t, m, a)));
            s.append(toOut("f(t)", Functions.Fl(t, m, a)));
            s.append(toOut(lambda + "(t)", Functions.Ll(t, m, a)));
        } else if (contains("k", "a", "t")) {
            double k = get("k");
            double a = get("a");
            double t = get("t");

            s.append(toOut("P(t)", Functions.Pv(k, a, t)));
            s.append(toOut("f(t)", Functions.Fv(k, a, t)));
            s.append(toOut("l(t)", Functions.Lv(k, a, t)));
            s.append(toOut("m(t)", Functions.Mv(k, a)));
        } else if (contains("t", sigma)) {
            double t = get("t");
            double a = get(sigma);

            s.append(toOut("P(t)", Functions.Pr(t, a)));
            s.append(toOut("f(t)", Functions.Fr(t, a)));
            s.append(toOut(lambda + "(t)", Functions.Lr(t, a)));
            s.append(toOut("m(t)", Functions.Mr(a)));
        }

        area.setText(s.toString());
    }

    public void processFunctions3(JTextArea area) {
        StringBuilder s = new StringBuilder();
        if (contains(lambda + "[]", "t")) {
            s.append(toOut("P", Reserv.P_c(getArray((lambda + "[]")), get("t"))));
        } else if (contains("m(t)[]")) {
            double[] m = toArray(map.get("m(t)[]"));
            double[] l = new double[m.length];
            for (int i = 0; i < m.length; i++)
                l[i] = ReservExp.L_i(m[i]);

            s.append(toOut(lambda + "(t)", ReservExp.L(l)));
            s.append(toOut("m(t)", ReservExp.M(l)));
        } else if (contains(lambda, "N", "t")) {
            double l = get(lambda);
            double n = get("N");
            double t = get("t");

            double lc = l * n;

            s.append(toOut(lambda + "(t)", lc));
            s.append(toOut("P(t)", ReservExp.P(lc, t)));
            s.append(toOut("q(t)", ReservExp.Q(lc, t)));
            s.append(toOut("f(t)", ReservExp.F(lc, t)));
            s.append(toOut("m(t)", ReservExp.M(lc)));
        } else if (contains("t", "p(t)[]")) {
            double t = get("t");
            double[] p = getArray("p(t)[]");

            double p_c = Basic.mult(p);
            double l_c = -Math.log(p_c) / t;
            double m_tc = ReservExp.M(l_c);

            s.append(toOut("P(t)", p_c));
            s.append(toOut(lambda, l_c));
            s.append(toOut("m(t)", m_tc));
        } else if (contains("P(t)", "N")) {
            double p = get("P(t)");
            double n = get("N");
            s.append(toOut("q(t)", 1 - p));
            s.append(toOut("P(t)", ReservOne.P_c(n, 1 - p)));
        } else if (contains("P_c(t)", "N")) {
            s.append(toOut("P(t)", ReservOne.P_i(get("N"), 1 - get("P_c(t)"))));
        }
        area.setText(s.toString());
    }

    public void processFunctions4(JTextArea area) {
        String s = "";
        if (contains("N", "m(t)", "t", "m")) {
            double n = get("N");
            double m_i = get("m(t)");
            double t = get("t");
            double m = get("m");

            double l = 1 / m_i * n;
            double m_c = ReservHotExp.M(l, m);
            double p_c = ReservHotExp.P(l, t, m);
            double f_c = ReservHotExp.F(l, t, m);
            double l_c = ReservHotExp.L(l, t, m);
            s += toOut("m(t)", m_c);
            s += toOut("P(t)", p_c);
            s += toOut("f(t)", f_c);
            s += toOut(lambda + "(t)", l_c);
        } else if (contains("N", "m(t)", "t")) {
            double n = get("N");
            double m_i = get("m(t)");
            double t = get("t");

            double l_c = 1 / m_i * n;
            double m_c = 1 / l_c;
            double p_c = ReservHotExp.P(l_c, t);
            double f_c = l_c * p_c;
            s += toOut(lambda + "(t)", l_c);
            s += toOut("m(t)", m_c);
            s += toOut("P(t)", p_c);
            s += toOut("f(t)", f_c);
        } else if (contains(lambda, "t", "m")) {
            double l = get(lambda);
            double t = get("t");
            double m = get("m");

            double m_c = ReservHotExp.M(l, m);
            double p_c = ReservHotExp.P(l, t, m);
            double f_c = ReservHotExp.F(l, t, m);
            double l_c = ReservHotExp.L(l, t, m);
            s += toOut("m(t)", m_c);
            s += toOut("P(t)", p_c);
            s += toOut("f(t)", f_c);
            s += toOut(lambda + "(t)", l_c);
        } else if (contains(lambda, "t")) {
            double l = get(lambda);
            double t = get("t");

            double m_c = 1 / l;
            double p_c = ReservHotExp.P(l, t);
            double f_c = l * p_c;
            s += toOut("m(t)", m_c);
            s += toOut("P(t)", p_c);
            s += toOut("f(t)", f_c);
        }
        area.setText(s);
    }

    public void processFunctions5(JTextArea area) {
        String s = "";
        if (contains("l0", "l1", "t")) {
            double l0 = get("l0");
            double l1 = get("l1");
            double m = 1;
            double t = get("t");
            s += toOut("P(t)", ReservNotHot.P(l0, l1, t, m));
            s += toOut("q(t)", ReservNotHot.Q(l0, l1, t, m));
            s += toOut("m(t)", ReservNotHot.M(l0, l1, m));
            s += toOut("f(t)", ReservNotHot.F(l0, l1, t, m));
            s += toOut(lambda + "(t)", ReservNotHot.L(l0, l1, t, m));
        } else if (contains("l0", "m", "t")) {
            double l0 = get("l0");
            double m = get("m");
            double t = get("t");
            s += toOut("P(t)", ReservNotHot.P(l0, t, m));
            s += toOut("q(t)", ReservNotHot.Q(l0, t, m));
            s += toOut("m(t)", ReservNotHot.M(l0, m));
            s += toOut("f(t)", ReservNotHot.F(l0, t, m));
            s += toOut(lambda + "(t)", ReservNotHot.L(l0, t, m));
        } else if (contains("N", "m(t)", "t")) {
            double n = get("N");
            double m_t = get("m(t)");
            double l0 = n / m_t;
            double t = get("t");
            s += toOut("P(t)", ReservNotHot.P(l0, t, 0));
            s += toOut("q(t)", ReservNotHot.Q(l0, t, 0));
            s += toOut("m(t)", ReservNotHot.M(l0, 0));
            s += toOut("f(t)", ReservNotHot.F(l0, t, 0));
            s += toOut(lambda + "(t)", ReservNotHot.L(l0, t, 0));
            s += "\n";
            s += toOut("P(t)", ReservNotHot.P(l0, t, 1));
            s += toOut("q(t)", ReservNotHot.Q(l0, t, 1));
            s += toOut("m(t)", ReservNotHot.M(l0, 1));
            s += toOut("f(t)", ReservNotHot.F(l0, t, 1));
            s += toOut(lambda + "(t)", ReservNotHot.L(l0, t, 1));
        }
        area.setText(s);
    }

    boolean contains(String... strings) {
        for (String string : strings)
            if (!map.containsKey(string)) return false;
        return true;
    }

    double get(String s) {
        return Double.parseDouble(map.get(s));
    }

    double[] getArray(String s) {
        return toArray(map.get(s));
    }

    public static double[] toArray(String s) {
        String[] strings = s.split(",");
        double[] doubles = new double[strings.length];
        for (int i = 0; i < strings.length; i++)
            doubles[i] = Double.parseDouble(strings[i]);
        return doubles;
    }

    String toOut(String s, double s1) {
        if (times.size() != 0) return s.replaceAll("t", String.valueOf(times.get(0))) + " = " + s1 + "\n";
        if (s.contains("(") && map.containsKey("t")) return s.replaceAll("t", map.get("t")) + " = " + s1 + "\n";
        return s + " = " + s1 + "\n";
    }

    public static String getVarHelp(String... strings) {
        StringBuilder s = new StringBuilder();
        for (String string : strings) {
            s.append(string).append(" : ").append(getVar(string)).append("\n");
        }
        return s.toString();
    }

    static String getVar(String s) {
        switch (s) {
            case "P(t)":
                return "Вероятность безотказной работы";
            case "N":
                return "Число изделий";
            case "n(t)":
                return "Число отказавших изделий";
            case "q(t)":
                return "Вероятность отказа";
            case "f(t)":
                return "Частота отказов";
            case "m(t)":
                return "Среднее время безотказной работы";
            case "t":
                return "Время";
            case lambda + "(t)":
                return "Интенсивность отказов";
            case "a":
                return "Распределение Вейбулла";
            case "k":
                return "Коэффициент распределения Вейбулла";
            case sigma:
                return "Распределение Рэлея";
        }
        return null;
    }
}
