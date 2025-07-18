package dev.deitylamb.fern.transitions.decorators;

import java.util.Arrays;

import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Formattable;
import dev.deitylamb.fern.transitions.ChainTransition;
import dev.deitylamb.fern.transitions.Transitionable;

public class DelayTransition<T> extends TransitionDecorator<T> {
    private final float delay;
    private float elapsed;

    public DelayTransition(Transitionable<T> transition, float delay) {
        super(transition);
        this.delay = delay;
    }

    @Override
    public void tick(T gui, float delta) {

        boolean run = isRunning();

        if (run && elapsed < delay) {
            this.apply(gui, 0f);
            this.elapsed = Fern.clamp(elapsed + delta, 0, delay);
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
    public Transitionable<T> chain(Transitionable<T> transition) {
        return new ChainTransition<>(Arrays.asList(this.clone(), transition.clone()));
    }

    @Override
    public Transitionable<T> reverse() {
        return new DelayTransition<>(inner.reverse(), delay);
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

        String tab = Formattable.indent(depth);

        return "DelayTransition {\n" +
                tab + Formattable.INDENT + "delay=" + delay + ",\n" +
                tab + Formattable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
                tab + "}";
    }
}
