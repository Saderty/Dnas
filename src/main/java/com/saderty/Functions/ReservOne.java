package com.saderty.Functions;

public class ReservOne {
    public static double P_c(double[] n){
        return 1-Basic.sum(n);
    }
    public static double P_c(double n,double q){
        return 1-n*q;
    }
    public static double P_i(double n,double q){
        return 1-q/n;
    }
}
