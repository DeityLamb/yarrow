package dev.deitylamb.fern.common;

import java.util.function.Function;

public class Easings {

    @FunctionalInterface
    public static interface Ease extends Function<Double, Double> {
    }

    public static Ease blend(Ease blend, Ease with) {
        return (alpha) -> with.apply(blend.apply(alpha));
    }

    public static Double linear(Double t) {
        return t;
    };

    public static Double easeInQuad(Double t) {
        return t * t;
    };

    public static Double easeOutQuad(Double t) {
        return 1 - (1 - t) * (1 - t);
    };

    public static Double easeInOutQuard(Double t) {
        return t < 0.5d ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

    public static Double easeInOutQuad(Double t) {
        return t < 0.5d ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

}
