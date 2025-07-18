package dev.deitylamb.fern.transitions;

import java.util.function.Function;

import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.Formattable;
import dev.deitylamb.fern.transitions.decorators.DelayTransition;
import dev.deitylamb.fern.transitions.decorators.LoopTransition;

public interface Transitionable<T> extends Flowable<T>, Formattable {

    float progress();

    default float progress(Function<Float, Float> ease) {
        return ease.apply(progress());
    }

    default int color(int from, int to) {
        return Fern.lerpColor(progress(), from, to);
    }

    default String colorHex(String from, String to) {
        return Fern.lerpHexColor(progress(), from, to);
    }

    void apply(T gui, float alpha);

    boolean isRunning();

    default boolean isPaused() {
        return !isRunning();
    }

    void run();

    void reset();

    void pause();

    default void restart() {
        this.reset();
        this.run();
    }

    default void stop() {
        this.pause();
        this.reset();
    }

    Transitionable<T> reverse();

    Transitionable<T> chain(Transitionable<T> transition);

    default Transitionable<T> then(Transitionable<T> transition) {
        return chain(transition);
    };

    default Transitionable<T> delay(float delay) {
        return new DelayTransition<>(this.clone(), delay);
    };

    default LoopTransition<T> loop() {
        return new LoopTransition<>(this);
    }

    default Transitionable<T> circular() {
        return this.then(this.reverse());
    }

    Transitionable<T> clone();
}
