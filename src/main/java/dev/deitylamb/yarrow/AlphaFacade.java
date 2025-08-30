package dev.deitylamb.yarrow;

import java.util.function.Function;

import dev.deitylamb.yarrow.common.Color;
import dev.deitylamb.yarrow.common.YarrowUtils;

interface AlphaFacade {

    double alpha();

    default double alpha(Function<Double, Double> ease) {
        return ease.apply(alpha());
    }

    default double lerp(double from, double to) {
        return YarrowUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(double from, double to, Function<Double, Double> ease) {
        return YarrowUtils.lerp(this.alpha(ease), from, to);
    }

    default int lerp(int from, int to) {
        return YarrowUtils.lerp(this.alpha(), from, to);
    }

    default int lerp(int from, int to, Function<Double, Double> ease) {
        return YarrowUtils.lerp(this.alpha(ease), from, to);
    }

    default double lerp(long from, long to) {
        return YarrowUtils.lerp(this.alpha(), from, to);
    }

    default long lerp(long from, long to, Function<Double, Double> ease) {
        return YarrowUtils.lerp(this.alpha(ease), from, to);
    }

    default float lerp(float from, float to) {
        return YarrowUtils.lerp(this.alpha(), from, to);
    }

    default float lerp(float from, float to, Function<Double, Double> ease) {
        return YarrowUtils.lerp(this.alpha(ease), from, to);
    }

    default Color lerp(Color from, Color to) {
        return Color.lerp(this.alpha(), from, to);
    }

    default Color lerp(Color from, Color to, Function<Double, Double> ease) {
        return Color.lerp(this.alpha(ease), from, to);
    }

}
