package dev.deitylamb.fern.transitions.decorators;

import dev.deitylamb.fern.transitions.Transitionable;

public abstract class TransitionDecorator<T> implements Transitionable<T> {

    protected final Transitionable<T> inner;

    TransitionDecorator(Transitionable<T> transition) {
        this.inner = transition;
    }

    // Each decorator should decide how to clone and in which order to apply itself.

    @Override
    public abstract TransitionDecorator<T> clone();

    @Override
    public abstract Transitionable<T> speed(double speed);

    @Override
    public abstract Transitionable<T> then(Transitionable<T> transition);

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
