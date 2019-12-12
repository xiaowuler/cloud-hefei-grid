package com.pingchun.utils;

public class Calc {
    public static double windSpeed(double u, double v){
        return Math.sqrt(Math.pow(u, 2) + Math.pow(v, 2));
    }

    public static double windDirection(double u, double v){
        double dird = Math.atan(u / v) * 180 / Math.PI;
        if (u != 0 && v < 0) {
            dird = dird + 180;
        } else if (u < 0 && v > 0) {
            dird = dird + 360;
        } else if (u == 0 && v > 0) {
            dird = 0;
        } else if (u == 0 && v < 0) {
            dird = 180;
        } else if (u > 0 && v == 0) {
            dird = 90;
        } else if (u < 0 && v == 0) {
            dird = 270;
        } else if (u == 0 && v == 0) {
            dird = -999;
        }
        return dird;
    }
}
