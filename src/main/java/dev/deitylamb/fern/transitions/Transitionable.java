package dev.deitylamb.fern.transitions;

import java.util.function.Function;

import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.transitions.decorators.DelayTransition;
import dev.deitylamb.fern.transitions.decorators.RepeatTransition;

public interface Transitionable<T> extends Flowable<T>, Displayable {

    Progress progress();

    default Progress progress(Function<Double, Double> ease) {
        return new Progress(alpha(ease));
    }

    double alpha();

    default double alpha(Function<Double, Double> ease) {
        return ease.apply(progress().alpha());
    }

    double duration();

    void apply(T gui, double alpha);

    boolean isRunning();

    default boolean isPaused() {
        return !isRunning();
    }

    void seek(double duration);

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

    Transitionable<T> ease(Ease ease);

    Transitionable<T> then(Transitionable<T> transition);

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

}
