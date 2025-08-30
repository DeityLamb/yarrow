package dev.deitylamb.yarrow;

import dev.deitylamb.yarrow.common.Displayable;
import dev.deitylamb.yarrow.common.Easings.Ease;
import dev.deitylamb.yarrow.common.YarrowUtils;
import dev.deitylamb.yarrow.flows.decorators.DelayFlow;
import dev.deitylamb.yarrow.flows.decorators.EaseFlow;
import dev.deitylamb.yarrow.flows.decorators.PubFlow;
import dev.deitylamb.yarrow.flows.decorators.RepeatFlow;
import dev.deitylamb.yarrow.flows.hooks.FlowSubscriber;

public interface Flow<T> extends Tickable<T>, SubscriberFacade<T>, AlphaFacade, Displayable {

    @Override
    void tick(T graphics, double delta);

    @Override
    default double alpha() {
        return YarrowUtils.clamp(elapsed() / duration(), 0d, 1d);
    }

    @Override
    default PubFlow<T> subscribe(FlowSubscriber<T> hook) {
        return new PubFlow<>(this.clone()).subscribe(hook);
    }

    double duration();

    double elapsed();

    default double remaining() {
        return duration() - elapsed();
    }

    boolean isRunning();

    default boolean isPaused() {
        return !isRunning();
    }

    default void seek(double duration) {
        this.reset();
        this.tick(duration);
    }

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

    Flow<T> speed(double speed);

    default Flow<T> ease(Ease ease) {
        return new EaseFlow<>(this.clone(), ease);
    }

    default Flow<T> reverse() {
        return this.ease(alpha -> 1d - alpha);
    }

    default Flow<T> delay(double delay) {
        return new DelayFlow<>(this.clone(), delay);
    }

    default RepeatFlow<T> repeat(int times) {
        return new RepeatFlow<>(this, times);
    }

    default RepeatFlow<T> loop() {
        return new RepeatFlow<>(this, -1);
    }

    default Flow<T> circular() {
        return this.then(this.reverse());
    }
}
