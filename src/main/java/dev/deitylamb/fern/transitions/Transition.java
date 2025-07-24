package dev.deitylamb.fern.transitions;

import java.util.Arrays;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.common.FernUtils;

public class Transition<T> implements Transitionable<T> {

    private boolean running = false;
    private double elapsed = 0;
    private final double duration;
    private final Ease ease;

    public Transition(double duration, Ease ease) {
        this.duration = duration;
        this.ease = ease;

    }

    public Transition(double duration) {
        this.duration = duration;
        this.ease = Easings::linear;
    }

    public double duration() {
        return duration;

    }

    public Progress progress() {
        return new Progress(ease.apply(alpha()));
    }

    public double alpha() {
        return FernUtils.clamp(elapsed / duration, 0d, 1d);

    }

    @Override
    public void tick(T gui, double delta) {

        if (!this.isRunning()) {
            return;
        }

        this.elapsed = FernUtils.clamp(elapsed + delta, 0, duration);

        if (alpha() == 1d) {
            this.pause();
        }
    }

    @Override
    public void seek(double duration) {
        this.elapsed = FernUtils.modulo(duration, this.duration);

        if (alpha() == 1d) {
            this.pause();
        }
    }

    @Override
    public void apply(T gui, double alpha) {
    }

    @Override
    public void clear(T gui) {
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void play() {
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
    public Transitionable<T> then(Transitionable<T> transition) {
        return new SequenceTransition<>(Arrays.asList(this.clone(), transition.clone()));
    }

    @Override
    public Transitionable<T> ease(Ease ease) {
        return new Transition<>(duration, Easings.blend(this.ease, ease));
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "Transition {\n" +
                tab + Displayable.INDENT + "alpha=" + alpha() + ",\n" +
                tab + Displayable.INDENT + "running=" + running + ",\n" +
                tab + Displayable.INDENT + "elapsed=" + elapsed + ",\n" +
                tab + Displayable.INDENT + "duration=" + duration + ",\n" +
                tab + "}";
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public Transition<T> clone() {
        return new Transition<>(duration, ease);
    }

}
