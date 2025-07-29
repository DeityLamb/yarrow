package dev.deitylamb.fern.transitions.decorators;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.transitions.Transitionable;

public class RepeatTransition<T> extends TransitionDecorator<T> {

    private final int times;
    private int repeats = 1;

    public RepeatTransition(Transitionable<T> transition, int times) {
        super(transition);
        this.times = Math.max(-1, times);
    }

    public int repeats() {
        return repeats;
    }

    @Override
    public void tick(T graphics, double delta) {

        boolean run = isRunning();

        if (delta > inner.duration()) {

            repeats = Math.min(repeats + (int) Math.floor(delta / inner.duration()), times);
            delta = delta % inner.duration();
        }

        super.tick(graphics, delta);

        if (run && !isRunning()) {

            if (!isInfinityLoop() && isOver()) {
                return;
            }

            this.repeats++;

            inner.restart();
        }
    }

    @Override
    public void reset() {
        super.reset();
        repeats = 0;
    }

    @Override
    public double duration() {
        return isInfinityLoop() ? -1 : times * inner.duration();
    }

    private boolean isOver() {
        return repeats >= times;
    }

    private boolean isInfinityLoop() {
        return times == -1;
    }

    @Override
    public RepeatTransition<T> speed(double speed) {
        return new RepeatTransition<>(inner.speed(speed), times);
    }

    @Override
    public RepeatTransition<T> then(Transitionable<T> transition) {
        return new RepeatTransition<>(inner.then(transition), times);
    }

    @Override
    public RepeatTransition<T> clone() {
        return new RepeatTransition<>(inner.clone(), times);
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "RepeatTransition {\n" +
                tab + Displayable.INDENT + "repeats=" + repeats + ",\n" +
                tab + Displayable.INDENT + "times=" + times + ",\n" +
                tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
                tab + "}";
    }

}
