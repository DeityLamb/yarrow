package dev.deitylamb.fern.transitions.decorators;

import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.transitions.Progress;
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
    public abstract Transitionable<T> ease(Ease ease);

    @Override
    public abstract Transitionable<T> then(Transitionable<T> transition);

    @Override
    public Progress progress() {
        return inner.progress();
    }

    @Override
    public double alpha() {
        return inner.alpha();
    }

    @Override
    public double duration() {
        return inner.duration();
    }

    @Override
    public void apply(T gui, double alpha) {
        this.inner.apply(gui, alpha);
    }

    @Override
    public void tick(T gui, double delta) {
        this.inner.tick(gui, delta);
    }

    @Override
    public void clear(T gui) {
        this.inner.clear(gui);
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
