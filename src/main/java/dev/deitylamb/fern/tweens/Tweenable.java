package dev.deitylamb.fern.tweens;

import java.util.Arrays;

@FunctionalInterface
public interface Tweenable<T> {
    void apply(T gui, double alpha);

    default void clear(T gui) {
    };

    default Tweenable<T> merge(Tweenable<T> with) {
        return new CompoundTween<>(Arrays.asList(clone(this), clone(with)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Tweenable<T> clone(Tweenable<T> tween) {
        try {
            return (Tweenable<T>) tween.getClass().getMethod("clone").invoke(tween);
        } catch (Exception exception) {
            return tween;
        }
    }

    public static <T> Tweenable<T> empty() {
        return (gui, alpha) -> {
        };
    }
}
