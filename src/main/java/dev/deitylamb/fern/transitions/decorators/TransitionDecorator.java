package dev.deitylamb.fern.transitions.decorators;

import dev.deitylamb.fern.transitions.Transitionable;

public abstract class TransitionDecorator<T> implements Transitionable<T> {

    protected final Transitionable<T> inner;

    TransitionDecorator(Transitionable<T> transition) {
        this.inner = transition;
    }

    @Override
    public abstract TransitionDecorator<T> clone();

    @Override
    public abstract Transitionable<T> chain(Transitionable<T> transition);

    @Override
    public abstract Transitionable<T> reverse();

    @Override
    public float progress() {
        return inner.progress();
    }

    @Override
    public void apply(T gui, float alpha) {
        this.inner.apply(gui, alpha);
    }

    @Override
    public void tick(T gui, float delta) {
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
    public void run() {
        this.inner.run();
    }

}
