package dev.deitylamb.fern.tweens;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class TweenBuilder<T> {

    public static interface TweenFactory<T> extends Function<TweenBuilder<T>, TweenBuilder<T>> {
        default Tweenable<T> create() {
            return this.apply(new TweenBuilder<>()).build();
        }
    }

    private CompoundTween<T> tween = new CompoundTween<>();

    private TweenBuilder() {
    }

    private TweenBuilder(Tweenable<T> tween) {
        this.tween = new CompoundTween<>(Arrays.asList(tween));
    }

    public static <T> TweenBuilder<T> from(Tweenable<T> tween) {
        return new TweenBuilder<T>(Tweenable.clone(tween));
    }

    public static <T> Tweenable<T> reverse(Tweenable<T> tween) {
        return from(tween).reverse().build();
    }

    public TweenBuilder<T> add(Tweenable<T> tween) {
        return from(new CompoundTween<>(Arrays.asList(this.tween, tween)));
    }

    public TweenBuilder<T> add(Tweenable<T> tween, Function<Double, Double> ease) {
        return and(builder -> builder.add(tween).via(ease));
    }

    public TweenBuilder<T> add(Tweenable<T> tween, Consumer<T> clear) {
        return from(new Tweenable<T>() {
            @Override
            public void apply(T graphics, double alpha) {
                tween.apply(graphics, alpha);
            }

            @Override
            public void clear(T graphics) {
                tween.clear(graphics);
                clear(graphics);
            }
        });
    }

    public TweenBuilder<T> via(Function<Double, Double> via) {
        return from(new CompoundTween<>(Arrays.asList(new PipeTween<T>(tween, via))));
    }

    public TweenBuilder<T> reverse() {
        return this.via((alpha) -> 1d - alpha);
    }

    public TweenBuilder<T> and(TweenFactory<T> factory) {
        return from(new CompoundTween<>(Arrays.asList(this.tween, factory.create())));
    }

    public TweenBuilder<T> and(TweenBuilder<T> builder) {
        return add(builder.build());
    }

    public TweenBuilder<T> and(Tweenable<T> tween) {
        return add(tween);
    }

    public Tweenable<T> build() {
        return Tweenable.clone(tween);
    }

}
