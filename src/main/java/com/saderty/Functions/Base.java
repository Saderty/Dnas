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
        if (map.containsKey("m[]")) {
            double[] m = Basic.toArray(map.get("m[]"));

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
            s += "f_c = " + ReservExp.Q(lc, t) + "\n";
            s += "m_c = " + ReservExp.M(lc) + "\n";
        }

        area.setText(s);
    }
}
