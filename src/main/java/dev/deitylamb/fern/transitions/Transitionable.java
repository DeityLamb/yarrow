package dev.deitylamb.fern.transitions;

import java.util.function.Function;

import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.common.Color;
import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.common.FernUtils;
import dev.deitylamb.fern.transitions.decorators.DelayTransition;
import dev.deitylamb.fern.transitions.decorators.EaseTransition;
import dev.deitylamb.fern.transitions.decorators.RepeatTransition;

public interface Transitionable<T> extends Flowable<T>, Displayable {

    double alpha();

    default double alpha(Function<Double, Double> ease) {
        return ease.apply(alpha());
    }

    double duration();

    void apply(T graphics, double alpha);

    boolean isRunning();

    default boolean isPaused() {
        return !isRunning();
    }

    default void seek(double duration) {
        this.reset();
        this.tick(duration);
    };

    void play();

    void reset();

    void pause();

    default void restart() {
        this.reset();
        this.play();
    }

    default void stop() {
        this.pause();
        this.reset();
    }

    Transitionable<T> clone();

    Transitionable<T> then(Transitionable<T> transition);

    default Transitionable<T> then(Function<Function<Double, Transitionable<T>>, Transitionable<T>> factory) {
        return this.then(factory.apply(duration -> Fern.transition(duration)));
    }

    Transitionable<T> speed(double speed);

    default Transitionable<T> ease(Ease ease) {
        return new EaseTransition<>(this.clone(), ease);
    };

    default Transitionable<T> reverse() {
        return this.ease(alpha -> 1d - alpha);
    };

    default Transitionable<T> delay(double delay) {
        return new DelayTransition<>(this.clone(), delay);
    };

    default RepeatTransition<T> repeat(int times) {
        return new RepeatTransition<>(this, times);
    }

    default RepeatTransition<T> loop() {
        return new RepeatTransition<>(this, -1);
    }

    default Transitionable<T> circular() {
        return this.then(this.reverse());
    }

    default double lerp(double from, double to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(double from, double to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default double lerp(int from, int to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(int from, int to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default double lerp(long from, long to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(long from, long to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default double lerp(float from, float to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(float from, float to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default Color lerp(Color from, Color to) {
        return Color.lerp(this.alpha(), from, to);
    }

    default Color lerp(Color from, Color to, Function<Double, Double> ease) {
        return Color.lerp(this.alpha(ease), from, to);
    }

}
