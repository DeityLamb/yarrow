package dev.deitylamb.fern.tweens;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TweenBuilder<T> {

    public static interface TweenFactory<T> extends Function<TweenBuilder<T>, TweenBuilder<T>> {
        default Tween<T> create() {
            return this.apply(new TweenBuilder<>()).build();
        }
    }

    private CompoundTween<T> tween = new CompoundTween<>();

    private TweenBuilder() {
    }

    private TweenBuilder(Tween<T> tween) {
        this.tween = new CompoundTween<>(Arrays.asList(tween));
    }

    public static <T> TweenBuilder<T> from(Tween<T> tween) {
        return new TweenBuilder<T>(Tween.clone(tween));
    }

    public static <T> Tween<T> reverse(Tween<T> tween) {
        return from(tween).reverse().build();
    }

    public TweenBuilder<T> add(Tween<T> tween) {
        return from(CompoundTween.from(this.tween, tween));
    }

    public TweenBuilder<T> add(Tween<T> tween, Function<Float, Float> ease) {
        return and(builder -> builder.add(tween).via(ease));
    }

    public TweenBuilder<T> add(Tween<T> tween, Consumer<T> clear) {
        return from(new Tween<T>() {
            @Override
            public void apply(T gui, float alpha) {
                tween.apply(gui, alpha);
            }

            @Override
            public void clear(T gui) {
                tween.clear(gui);
                clear(gui);
            }
        });
    }

    public TweenBuilder<T> via(Function<Float, Float> via) {
        return from(CompoundTween.from(new PipeTween<T>(tween, via)));
    }

    public TweenBuilder<T> reverse() {
        return this.via((alpha) -> 1f - alpha);
    }

    public TweenBuilder<T> and(TweenFactory<T> factory) {
        return from(CompoundTween.from(this.tween, factory.create()));
    }

    public TweenBuilder<T> and(TweenBuilder<T> builder) {
        return add(builder.build());
    }

    public TweenBuilder<T> and(Tween<T> tween) {
        return add(tween);
    }

    public Tween<T> build() {
        return Tween.clone(tween);
    }

}
