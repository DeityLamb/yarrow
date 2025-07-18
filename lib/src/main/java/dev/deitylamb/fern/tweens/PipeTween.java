package dev.deitylamb.fern.tweens;

import java.util.function.Function;

public class PipeTween<T> implements Tween<T> {

    private Tween<T> transition;
    private Function<Float, Float> via;

    public PipeTween(Tween<T> transition, Function<Float, Float> via) {
        this.transition = transition;
        this.via = via;
    }

    public Tween<T> transition() {
        return transition;
    }

    @Override
    public void apply(T gui, float alpha) {
        this.transition.apply(gui, this.via.apply(alpha));
    }

    @Override
    public void clear(T gui) {
        this.transition.clear(gui);
    }

    public PipeTween<T> clone() {
        return new PipeTween<>(Tween.clone(this.transition), this.via);
    }
}
