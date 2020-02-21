package com.saderty.Functions;

public class Reserv {
    public static double P_c(double[] nums) {
        return Basic.mult(nums);
    }

    public static double L_c(double[] nums) {
        return Basic.sum(nums);
    }
    public static double Q_c(double[] nums){
        return 1-Basic.mult(nums);
    }
}
