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
            if (tmp[0].contains("("))
                if (!tmp[0].substring(tmp[0].indexOf("(") + 1, tmp[0].indexOf(")")).equals("t"))
                    times.add(Integer.valueOf(tmp[0].substring(tmp[0].indexOf("(") + 1, tmp[0].indexOf(")"))));
            if (tmp.length == 1) continue;
            map.put(tmp[0], tmp[1]);
        }
    }

    public void processFunctions1(JTextArea area) {
        StringBuilder s = new StringBuilder();
        for (int time : times) {
            if (contains("N", "n(" + time + ")")) {
                double n = get("N");
                double nt = get("n(" + time + ")");
                s.append(toOut("P(t)", Functions.Ps(n, nt)));
                s.append(toOut("q(t)", Functions.Qs(n, nt)));
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
            s.append("m' = ").append(Functions.Ms(ts)).append("\n");
        }
        if (contains("nt[]", "t")) {
            String[] ts = map.get("nt[]").split(",");
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

        if (contains("l", "t")) {
            double t = get("t");
            double l = get("l");

            s.append(toOut("P(t)", Functions.P(t, l)));
            s.append(toOut("q(t)", Functions.Q(t, l)));
            s.append(toOut("f(t)", Functions.F(t, l)));
            s.append(toOut("m(t)", Functions.M(l)));
        } else if (contains("m", "a", "t")) {
            double m = get("m");
            double a = get("a");
            double t = get("t");

            s.append(toOut("P(t)", Functions.Pl(t, m, a)));
            s.append(toOut("f(t)", Functions.Fl(t, m, a)));
            s.append(toOut("l(t)", Functions.Ll(t, m, a)));
        } else if (contains("k", "a", "t")) {
            double k = get("k");
            double a = get("a");
            double t = get("t");

            s.append(toOut("P(t)", Functions.Pv(k, a, t)));
            s.append(toOut("f(t)", Functions.Fv(k, a, t)));
            s.append(toOut("l(t)", Functions.Lv(k, a, t)));
            s.append(toOut("m(t)", Functions.Mv(k, a)));
        } else if (contains("t", "a")) {
            double t = get("t");
            double a = get("a");

            s.append(toOut("P(t)", Functions.Pr(t, a)));
            s.append(toOut("f(t)", Functions.Fr(t, a)));
            s.append(toOut("l(t)", Functions.Lr(t, a)));
            s.append(toOut("m(t)", Functions.Mr(a)));
        }

        area.setText(s.toString());
    }

    public void processFunctions3(JTextArea area) {
        StringBuilder s = new StringBuilder();
        if (contains("l[]", "t")) {
            s.append(toOut("p_c", Reserv.P_c(getArray(("l[]")), get("t"))));
        } else if (contains("m[]")) {
            double[] m = toArray(map.get("m[]"));
            double[] l = new double[m.length];
            for (int i = 0; i < m.length; i++)
                l[i] = ReservExp.L_i(m[i]);

            s.append(toOut("l(t)", ReservExp.L(l)));
            s.append(toOut("m(t)", ReservExp.M(l)));
        } else if (contains("l", "n", "t")) {
            double l = get("l");
            double n = get("n");
            double t = get("t");

            double lc = l * n;

            s.append(toOut("l(t)", lc));
            s.append(toOut("P(t)", ReservExp.P(lc, t)));
            s.append(toOut("P(t)", ReservExp.Q(lc, t)));
            s.append(toOut("P(t)", ReservExp.F(lc, t)));
            s.append(toOut("P(t)", ReservExp.M(lc)));
        } else if (contains("t", "p[]")) {
            double t = get("t");
            double[] p = getArray("p[]");

            double p_c = Basic.mult(p);
            double l_c = -Math.log(p_c) / t;
            double m_tc = ReservExp.M(l_c);

            s.append(toOut("p_c", p_c));
            s.append(toOut("l_c", l_c));
            s.append(toOut("m_tc", m_tc));
        } else if (contains("p", "n")) {
            double p = get("p");
            double n = get("n");
            s.append(toOut("q", 1 - p));
            s.append(toOut("p_c", ReservOne.P_c(n, 1 - p)));
        } else if (contains("p_c", "n")) {
            s.append(toOut("p_i", ReservOne.P_i(get("n"), 1 - get("p_c"))));
        }
        area.setText(s.toString());
    }

    public void processFunctions4(JTextArea area) {
        String s = "";
        if (contains("n", "m_i", "t", "m")) {
            double n = get("n");
            double m_i = get("m_i");
            double t = get("t");
            double m = get("m");

            double l = 1 / m_i * n;
            double m_c = ReservHotExp.M(l, m);
            double p_c = ReservHotExp.P(l, t, m);
            double f_c = ReservHotExp.F(l, t, m);
            double l_c = ReservHotExp.L(l, t, m);
            s += toOut("m_c", m_c);
            s += toOut("p_c", p_c);
            s += toOut("f_c", f_c);
            s += toOut("l_c", l_c);
        } else if (contains("n", "m_i", "t")) {
            double n = get("n");
            double m_i = get("m_i");
            double t = get("t");

            double l_c = 1 / m_i * n;
            double m_c = 1 / l_c;
            double p_c = ReservHotExp.P(l_c, t);
            double f_c = l_c * p_c;
            s += toOut("l_c", l_c);
            s += toOut("m_c", m_c);
            s += toOut("p_c", p_c);
            s += toOut("f_c", f_c);
        } else if (contains("l", "t", "m")) {
            double l = get("l");
            double t = get("t");
            double m = get("m");

            double m_c = ReservHotExp.M(l, m);
            double p_c = ReservHotExp.P(l, t, m);
            double f_c = ReservHotExp.F(l, t, m);
            double l_c = ReservHotExp.L(l, t, m);
            s += toOut("m_c", m_c);
            s += toOut("p_c", p_c);
            s += toOut("f_c", f_c);
            s += toOut("l_c", l_c);
        } else if (contains("l", "t")) {
            double l = get("l");
            double t = get("t");

            double m_c = 1 / l;
            double p_c = ReservHotExp.P(l, t);
            double f_c = l * p_c;
            s += toOut("m_c", m_c);
            s += toOut("p_c", p_c);
            s += toOut("f_c", f_c);
        } else if (contains("l0", "l1", "m", "t")) {
            double l0 = get("l0");
            double l1 = get("l1");
            double m = get("m");
            double t = get("t");
            s += toOut("P", ReservNotHot.P(l0, l1, t, m));
            s += toOut("q", ReservNotHot.Q(l0, l1, t, m));
            s += toOut("m", ReservNotHot.M(l0, l1, m));
            s += toOut("f", ReservNotHot.F(l0, l1, t, m));
            s += toOut("l", ReservNotHot.L(l0, l1, t, m));
        } else if (contains("l0", "m", "t")) {
            double l0 = get("l0");
            double m = get("m");
            double t = get("t");
            s += toOut("P", ReservNotHot.P(l0, t, m));
            s += toOut("q", ReservNotHot.Q(l0, t, m));
            s += toOut("m", ReservNotHot.M(l0, m));
            s += toOut("f", ReservNotHot.F(l0, t, m));
            s += toOut("l", ReservNotHot.L(l0, t, m));
        } else if (contains("n", "m", "t")) {
            double n = get("n");
            double m_t = get("m");
            double l0 = n / m_t;
            double t = get("t");
            s += toOut("P", ReservNotHot.P(l0, t, 0));
            s += toOut("q", ReservNotHot.Q(l0, t, 0));
            s += toOut("m", ReservNotHot.M(l0, 0));
            s += toOut("f", ReservNotHot.F(l0, t, 0));
            s += toOut("l", ReservNotHot.L(l0, t, 0));
            s += "\n";
            s += toOut("P", ReservNotHot.P(l0, t, 1));
            s += toOut("q", ReservNotHot.Q(l0, t, 1));
            s += toOut("m", ReservNotHot.M(l0, 1));
            s += toOut("f", ReservNotHot.F(l0, t, 1));
            s += toOut("l", ReservNotHot.L(l0, t, 1));
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
        if (s.contains("(")) return s.replaceAll("t", map.get("t")) + " = " + s1 + "\n";
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
            case lambda+"(t)":
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
