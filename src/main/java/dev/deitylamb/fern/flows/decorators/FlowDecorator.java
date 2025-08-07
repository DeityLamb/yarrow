package dev.deitylamb.fern.flows.decorators;

import dev.deitylamb.fern.flows.Flow;

public abstract class FlowDecorator<T> implements Flow<T> {

    protected final Flow<T> inner;

    FlowDecorator(Flow<T> inner) {
        this.inner = inner;
    }

    // Each decorator should decide how to clone and in which order to apply itself.

    @Override
    public abstract FlowDecorator<T> clone();

    @Override
    public abstract Flow<T> speed(double speed);

    @Override
    public abstract Flow<T> then(Flow<T> flow);

    @Override
    public double alpha() {
        return inner.alpha();
    }

    @Override
    public double duration() {
        return inner.duration();
    }

    @Override
    public void tick(T graphics, double delta) {
        this.inner.tick(graphics, delta);

    }

    @Override
    public boolean isRunning() {
        return this.inner.isRunning();
    }

    @Override
    public void pause() {
        this.inner.pause();
    }

    @Override
    public void reset() {
        this.inner.reset();
    }

    @Override
    public void play() {
        this.inner.play();
    }

}
