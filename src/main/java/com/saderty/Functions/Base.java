package com.saderty.Functions;

import javax.swing.*;
import java.util.*;

public class Base {
    private Map<String, String> map = new HashMap<>();
    private List<Integer> times = new LinkedList<>();

    public Base(JTextArea area) {
        String[] numLines = area.getText().split("\n");
        for (String numLine : numLines) {
            String[] tmp = numLine.split("=");
            if (tmp[0].contains("("))
                times.add(Integer.valueOf(tmp[0].substring(tmp[0].indexOf("(") + 1, tmp[0].indexOf(")"))));
            map.put(tmp[0], tmp[1]);
        }
    }

    public void processFunctions1(JTextArea area) {
        StringBuilder s = new StringBuilder();
        for (int time : times) {
            if (map.containsKey("N") && map.containsKey("n(" + time + ")")) {
                s.append("P'(").append(time).append(") = ")
                        .append(Functions.Ps(Double.parseDouble(map.get("N")),
                                Double.parseDouble(map.get("n(" + time + ")"))))
                        .append("\n");
                s.append("q'(").append(time).append(") = ")
                        .append(Functions.Qs(Double.parseDouble(map.get("N")),
                                Double.parseDouble(map.get("n(" + time + ")"))))
                        .append("\n");
            }
        }
        s.append("\n");
        if (times.size() == 2) {
            double n = Double.parseDouble(map.get("N"));
            double nt1 = Double.parseDouble(map.get("n(" + times.get(1) + ")"));
            double nt0 = Double.parseDouble(map.get("n(" + times.get(0) + ")"));
            double ns = n - (nt0 + nt1) / 2;
            s.append("f'(").append(times.get(0)).append(") = ")
                    .append(Functions.Fs((int) (nt1 - nt0), n, times.get(1) - times.get(0)))
                    .append("\n");
            s.append("\u03bb'(").append(times.get(0)).append(") = ")
                    .append(Functions.Fs((int) (nt1 - nt0), ns, times.get(1) - times.get(0)))
                    .append("\n");
        }

        if (map.containsKey("t[]")) {
            String[] ts = map.get("t[]").split(",");
            s.append("m' = ").append(Functions.Ms(ts)).append("\n");
        }

        if (map.containsKey("nt[]") && map.containsKey("t")) {
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

            s.append("m' = ").append(sum).append("\n");
        }

        area.setText(s.toString());
    }

    public void processFunctions2(JTextArea area) {
        String s = "";

        if (map.containsKey("l") && map.containsKey("t")) {
            double t = Double.parseDouble(map.get("t"));
            double l = Double.parseDouble(map.get("l"));

            s += "P(" + map.get("t") + ") = " + Functions.P(t, l) + "\n";
            s += "q(" + map.get("t") + ") = " + Functions.Q(t, l) + "\n";
            s += "f(" + map.get("t") + ") = " + Functions.F(t, l) + "\n";
            s += "m_t = " + Functions.M(l) + "\n";
        } else if (map.containsKey("m") && map.containsKey("a") && map.containsKey("t")) {
            double m = Double.parseDouble(map.get("m"));
            double a = Double.parseDouble(map.get("a"));
            double t = Double.parseDouble(map.get("t"));

            s += "U = " + Functions.Ul(t, m, a) + "\n";
            s += "P(" + map.get("t") + ") = " + Functions.Pl(t, m, a) + "\n";
            s += "f(" + map.get("t") + ") = " + Functions.Fl(t, m, a) + "\n";
            s += "l(" + map.get("t") + ") = " + Functions.Ll(t, m, a) + "\n";
        } else if (map.containsKey("k") && map.containsKey("a") && map.containsKey("t")) {
            double k = Double.parseDouble(map.get("k"));
            double a = Double.parseDouble(map.get("a"));
            double t = Double.parseDouble(map.get("t"));

            s += "P(" + map.get("t") + ") = " + Functions.Pv(k, a, t) + "\n";
            s += "f(" + map.get("t") + ") = " + Functions.Fv(k, a, t) + "\n";
            s += "l(" + map.get("t") + ") = " + Functions.Lv(k, a, t) + "\n";
            s += "m_t = " + Functions.Mv(k, a) + "\n";
        } else if (map.containsKey("t") && map.containsKey("a")) {
            double t = Double.parseDouble(map.get("t"));
            double a = Double.parseDouble(map.get("a"));

            s += "P(" + map.get("t") + ") = " + Functions.Pr(t, a) + "\n";
            s += "f(" + map.get("t") + ") = " + Functions.Fr(t, a) + "\n";
            s += "l(" + map.get("t") + ") = " + Functions.Lr(t, a) + "\n";
            s += "m_t = " + Functions.Mr(a) + "\n";
        }

        area.setText(s);
    }

    public void processFunctions3(JTextArea area) {
        String s = "";
        if (contains("l[]", "t")) {
            s += toOut("p_c", Reserv.P_c(getArray(("l[]")), get("t")), get("t"));
        } else if (map.containsKey("m[]")) {
            double[] m = toArray(map.get("m[]"));

            double[] l = new double[m.length];
            for (int i = 0; i < m.length; i++)
                l[i] = ReservExp.L_i(m[i]);

            s += "l_c = " + ReservExp.L(l) + "\n";
            s += "m_tc = " + ReservExp.M(l) + "\n";
        } else if (map.containsKey("l") && map.containsKey("n") && map.containsKey("t")) {
            double l = Double.parseDouble(map.get("l"));
            double n = Double.parseDouble(map.get("n"));
            double t = Double.parseDouble(map.get("t"));

            double lc = l * n;

            s += "l_c = " + lc + "\n";
            s += "P_c = " + ReservExp.P(lc, t) + "\n";
            s += "q_c = " + ReservExp.Q(lc, t) + "\n";
            s += "f_c = " + ReservExp.F(lc, t) + "\n";
            s += "m_c = " + ReservExp.M(lc) + "\n";
        } else if (contains("t", "p[]")) {
            double t = get("t");
            double[] p = getArray("p[]");

            double p_c = Basic.mult(p);
            double l_c = -Math.log(p_c) / t;
            double m_tc = ReservExp.M(l_c);

            s += toOut("p_c", p_c, t);
            s += toOut("l_c", l_c);
            s += toOut("m_tc", m_tc);
        } else if (contains("p", "n")) {
            double p = get("p");
            double n = get("n");
            s += toOut("q", 1 - p);
            s += toOut("p_c", ReservOne.P_c(n, 1 - p));
        } else if (contains("p_c", "n")) {
            s += toOut("p_i", ReservOne.P_i(get("n"), 1 - get("p_c")));
        }

        area.setText(s);
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

    public static String trimBorders(String s) {
        return s.substring(1, s.length() - 1);
    }

    public static double[] toArray(String s) {
        String[] strings = s.split(",");
        double[] doubles = new double[strings.length];
        for (int i = 0; i < strings.length; i++)
            doubles[i] = Double.parseDouble(strings[i]);
        return doubles;
    }

    String toOut(String s, double s1) {
        return s + " = " + s1 + "\n";
    }

    String toOut(String s, double s1, double t) {
        return s + "(" + t + ") = " + s1 + "\n";
    }
}
