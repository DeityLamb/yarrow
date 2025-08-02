package dev.deitylamb.fern.transitions;

import java.util.Arrays;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.FernUtils;

public class Transition<T> implements Transitionable<T> {

    private boolean running = false;
    private double elapsed = 0;
    private final double duration;

    public Transition(double duration) {
        this.duration = duration;

    }

    public double duration() {
        return duration;

    }

    public double alpha() {
        return FernUtils.clamp(elapsed / duration, 0d, 1d);

    }

    @Override
    public void tick(T graphics, double delta) {

        if (!this.isRunning()) {
            return;
        }

        this.elapsed = FernUtils.clamp(elapsed + delta, 0, duration);

        if (isEnded()) {
            this.pause();
        }
    }

    public boolean isEnded() {
        return alpha() == 1d;
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
    public Transitionable<T> speed(double speed) {
        return new Transition<>(Math.max(0, duration / speed));
    }

    @Override
    public Transitionable<T> then(Transitionable<T> transition) {
        return new SequenceTransition<>(Arrays.asList(this.clone(), transition.clone()));
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
        return new Transition<>(duration);
    }

}
