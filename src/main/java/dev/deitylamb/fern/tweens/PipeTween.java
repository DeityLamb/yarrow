package dev.deitylamb.fern.tweens;

import java.util.function.Function;

public class PipeTween<T> implements Tweenable<T> {

    private Tweenable<T> transition;
    private Function<Double, Double> via;

    public PipeTween(Tweenable<T> transition, Function<Double, Double> via) {
        this.transition = transition;
        this.via = via;
    }

    public Tweenable<T> transition() {
        return transition;
    }

    @Override
    public void apply(T graphics, double alpha) {
        this.transition.apply(graphics, this.via.apply(alpha));
    }

    @Override
    public void clear(T graphics) {
        this.transition.clear(graphics);
    }

    public PipeTween<T> clone() {
        return new PipeTween<>(Tweenable.clone(this.transition), this.via);
    }
}
