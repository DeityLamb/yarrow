package dev.deitylamb.fern;

import java.util.function.Function;

public class Easings {

    public static interface Ease extends Function<Float, Float> {
    }

    public static Ease blend(Ease blend, Ease with) {
        return (alpha) -> with.apply(blend.apply(alpha));
    }

    public static Float linear(Float t) {
        return t;
    };

    public static Float easeInQuad(Float t) {
        return t * t;
    };

    public static Float easeOutQuad(Float t) {
        return 1 - (1 - t) * (1 - t);
    };

    public static Float easeInOutQuard(Float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

    public static Float easeInOutQuad(Float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

}
