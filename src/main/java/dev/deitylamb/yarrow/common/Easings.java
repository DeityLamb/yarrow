package dev.deitylamb.yarrow.common;

import java.util.function.Function;

public class Easings {

    @FunctionalInterface
    public static interface Ease extends Function<Double, Double> {
    }

    public static Ease bezierLinear(double p0, double p1) {
        return (t) -> {
            double mt = 1 - t;
            return mt * p0 + t * p1;
        };
    }

    public static Ease bezierQuad(double p0, double p1, double p2) {
        return (t) -> {
            double mt = 1 - t;
            return mt * mt * p0
                    + 2 * mt * t * p1
                    + t * t * p2;
        };
    }

    public static Ease bezierCubic(double x1, double y1, double x2, double y2) {
        return (Double t) -> {

            double x = t;
            for (int i = 0; i < 5; i++) {
                double mt = 1 - x;
                double f = (3 * mt * mt * x * x1)
                        + (3 * mt * x * x * x2)
                        + (x * x * x) - t;
                double df = 3 * (1 - x) * (1 - x) * (x1)
                        + 6 * (1 - x) * x * (x2 - x1)
                        + 3 * x * x * (1 - x2);
                if (Math.abs(df) < 1e-6) {
                    break;
                }
                x -= f / df;
                x = Math.max(0, Math.min(1, x));
            }

            return 3 * (1 - x) * (1 - x) * x * y1
                    + 3 * (1 - x) * x * x * y2
                    + x * x * x;
        };
    }

    public static Ease blend(Ease blend, Ease with) {
        return (alpha) -> with.apply(blend.apply(alpha));
    }

    public static Double linear(Double t) {
        return t;
    }

    ;

    public static Double easeInQuad(Double t) {
        return t * t;
    }

    ;

    public static Double easeOutQuad(Double t) {
        return 1 - (1 - t) * (1 - t);
    }

    ;

    public static Double easeInOutQuad(Double t) {
        return t < 0.5d ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    ;

    public static Double easeInCubic(Double t) {
        return t * t * t;
    }

    public static Double easeOutCubic(Double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    public static Double easeInOutCubic(Double t) {
        return t < 0.5d
                ? 4 * t * t * t
                : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }

}
