package dev.deitylamb.fern.transitions.decorators;

import java.util.Arrays;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.common.FernUtils;
import dev.deitylamb.fern.transitions.SequenceTransition;
import dev.deitylamb.fern.transitions.Transitionable;

public class DelayTransition<T> extends TransitionDecorator<T> {
    private final double delay;
    private double elapsed = 0;

    public DelayTransition(Transitionable<T> transition, double delay) {
        super(transition);
        this.delay = delay;
    }

    @Override
    public void tick(T gui, double delta) {

        boolean run = isRunning();

        if (run && elapsed < delay) {
            this.apply(gui, 0d);
            this.elapsed = FernUtils.clamp(elapsed + delta, 0, delay);
            return;
        }

        super.tick(gui, delta);

        if (run && isPaused()) {
            this.elapsed = 0;
        }
    }

    @Override
    public void reset() {
        super.reset();
        elapsed = 0;
    }

    @Override
    public void seek(double duration) {

        double modulo = FernUtils.modulo(duration, this.duration());

        if (modulo > delay) {
            inner.seek(modulo - delay);
            this.elapsed = delay;
            return;
        }

        this.elapsed = modulo;
    }

    @Override
    public double duration() {
        return delay + inner.duration();
    }

    @Override
    public Transitionable<T> then(Transitionable<T> transition) {
        return new SequenceTransition<>(Arrays.asList(this.clone(), transition.clone()));
    }

    @Override
    public Transitionable<T> ease(Ease ease) {
        return new DelayTransition<>(inner.ease(ease), delay);
    }

    @Override
    public TransitionDecorator<T> clone() {
        return new DelayTransition<>(inner.clone(), delay);
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "DelayTransition {\n" +
                tab + Displayable.INDENT + "elapsed=" + elapsed + ",\n" +
                tab + Displayable.INDENT + "delay=" + delay + ",\n" +
                tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
                tab + "}";
    }
}
