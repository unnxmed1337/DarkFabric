package com.github.darkforge.util;

public class MathUtils {

    // TODO: 21/02/2018 Add More Utils

    public static <T extends Number> double wrap(T from, T min, T max) {

        double f = from.doubleValue();
        double mi = min.doubleValue();
        double ma = max.doubleValue();
        double di = ma - mi;
        return (((f - mi) % di) + di) % di + mi;
    }

    public static double getIncremental(double value, double increment) {

        double one = 1 / increment;
        return Math.round(value * one) / one;
    }

    public static float getIncremental(float value, float increment) {

        return (float) getIncremental((double) value, (double) increment);
    }


    public static int getIncremental(int value, int increment) {

        return (int) getIncremental((double) value, (double) increment);
    }

    public static <T extends Number> double parabolic(T from, T to, T inc) {

        return from.doubleValue() + ((to.doubleValue() - from.doubleValue()) / inc.doubleValue());
    }

    public static <T extends Number> double parabolic(T from, boolean var, T inc) {

        return parabolic(from, var ? 1 : 0, inc);
    }

    public static <T extends Number> double getDistance(T x1, T y1, T x2, T y2) {

        return Math.hypot(x2.doubleValue() - x1.doubleValue(), y2.doubleValue() - y1.doubleValue());
    }

    public static <T extends Number> double getDistance(T x1, T y1, T z1, T x2, T y2, T z2) {

        return Math.hypot(getDistance(x1, z1, x2, z2), y2.doubleValue() - y1.doubleValue());
    }

    public static double distTo(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double dirTo(double x1, double y1, double x2, double y2) {
        return Math.toDegrees(Math.atan2(y1 - y2, x1 - x2));
    }

}
