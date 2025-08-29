package dev.deitylamb.fern;

import java.util.function.Function;

import dev.deitylamb.fern.common.Color;
import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.common.FernUtils;
import dev.deitylamb.fern.flows.decorators.DelayFlow;
import dev.deitylamb.fern.flows.decorators.EaseFlow;
import dev.deitylamb.fern.flows.decorators.RepeatFlow;

public interface Flow<T> extends Tickable<T>, Displayable {

    double alpha();

    default double alpha(Function<Double, Double> ease) {
        return ease.apply(alpha());
    }

    void tick(T graphics, double delta);

    default void tick(double delta) {
        this.tick(null, delta);
    }

    double duration();

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

    Flow<T> clone();

    Flow<T> then(Flow<T> flow);

    default Flow<T> then(Function<Function<Double, Flow<T>>, Flow<T>> factory) {
        return this.then(factory.apply(duration -> Fern.flow(duration)));
    }

    Flow<T> speed(double speed);

    default Flow<T> ease(Ease ease) {
        return new EaseFlow<>(this.clone(), ease);
    };

    default Flow<T> reverse() {
        return this.ease(alpha -> 1d - alpha);
    };

    default Flow<T> delay(double delay) {
        return new DelayFlow<>(this.clone(), delay);
    };

    default RepeatFlow<T> repeat(int times) {
        return new RepeatFlow<>(this, times);
    }

    default RepeatFlow<T> loop() {
        return new RepeatFlow<>(this, -1);
    }

    default Flow<T> circular() {
        return this.then(this.reverse());
    }

    default double lerp(double from, double to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default double lerp(double from, double to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default int lerp(int from, int to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default int lerp(int from, int to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default double lerp(long from, long to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default long lerp(long from, long to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default float lerp(float from, float to) {
        return FernUtils.lerp(this.alpha(), from, to);
    }

    default float lerp(float from, float to, Function<Double, Double> ease) {
        return FernUtils.lerp(this.alpha(ease), from, to);
    }

    default Color lerp(Color from, Color to) {
        return Color.lerp(this.alpha(), from, to);
    }

    default Color lerp(Color from, Color to, Function<Double, Double> ease) {
        return Color.lerp(this.alpha(ease), from, to);
    }

}
