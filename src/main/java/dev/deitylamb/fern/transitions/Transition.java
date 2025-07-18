package dev.deitylamb.fern.transitions;

import java.util.Arrays;

import dev.deitylamb.fern.Easings;
import dev.deitylamb.fern.Easings.Ease;
import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Formattable;
import dev.deitylamb.fern.tweens.Tween;

public class Transition<T> implements Transitionable<T> {

    private boolean running = false;
    private float elapsed = 0f;
    private final float duration;
    private final Ease ease;

    private Tween<T> tween;

    public Transition(float duration, Tween<T> tween, Ease ease) {
        this.tween = tween;
        this.duration = duration;
        this.ease = ease;

    }

    public Transition(float duration, Tween<T> tween) {
        this.tween = tween;
        this.duration = duration;
        this.ease = Easings::linear;
    }

    public float lerp(float start, float end) {
        return Fern.lerp(progress(), start, end);
    }

    public float progress() {
        return ease.apply(linearProgress());
    }

    public float linearProgress() {
        return Fern.clamp(elapsed / duration, 0f, 1f);

    }

    @Override
    public void tick(T gui, float delta) {

        if (!this.isRunning()) {
            return;
        }

        this.elapsed = Fern.clamp(elapsed + delta, 0, duration);

        this.apply(gui, progress());

        if (linearProgress() == 1f) {
            this.stop();
        }
    }

    @Override
    public void apply(T gui, float alpha) {
        this.tween.apply(gui, alpha);
    }

    @Override
    public void clear(T gui) {
        this.tween.clear(gui);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        this.running = true;
    }

    @Override
    public void reset() {
        this.elapsed = 0;
    }

    @Override
    public void pause() {
        this.running = false;
    }

    @Override
    public Transitionable<T> chain(Transitionable<T> transition) {
        return new ChainTransition<>(Arrays.asList(this.clone(), transition.clone()));
    }

    @Override
    public Transitionable<T> reverse() {
        return new Transition<>(duration, Tween.clone(tween), Easings.blend(ease, (alpha) -> 1f - alpha));
    }

    @Override
    public String display(int depth) {

        String tab = Formattable.indent(depth);

        return "Transition {\n" +
                tab + Formattable.INDENT + "duration=" + duration + ",\n" +
                tab + Formattable.INDENT + "tween=" + tween + "\n" +
                tab + "}";
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public Transition<T> clone() {
        return new Transition<>(duration, Tween.clone(tween), ease);
    }

}
