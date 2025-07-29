package dev.deitylamb.fern.transitions.decorators;

import java.util.Arrays;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.FernUtils;
import dev.deitylamb.fern.transitions.SequenceTransition;
import dev.deitylamb.fern.transitions.Transitionable;

public class DelayTransition<T> extends TransitionDecorator<T> {
    public final double delay;
    private double elapsed = 0;

    public DelayTransition(Transitionable<T> transition, double delay) {
        super(transition);
        this.delay = delay;
    }

    @Override
    public void tick(T graphics, double delta) {

        boolean run = isRunning();

        if (delta > delay) {
            delta = delta - delay;
            this.elapsed = delay;
        }

        if (run && elapsed < delay) {
            this.apply(graphics, 0d);
            this.elapsed = FernUtils.clamp(elapsed + delta, 0, delay);
            return;
        }

        super.tick(graphics, delta);

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
    public double duration() {
        return delay + inner.duration();
    }

    @Override
    public DelayTransition<T> speed(double speed) {
        return new DelayTransition<>(inner.speed(speed), delay / speed);
    }

    @Override
    public SequenceTransition<T> then(Transitionable<T> transition) {
        return new SequenceTransition<>(Arrays.asList(this.clone(), transition.clone()));
    }

    @Override
    public DelayTransition<T> clone() {
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
