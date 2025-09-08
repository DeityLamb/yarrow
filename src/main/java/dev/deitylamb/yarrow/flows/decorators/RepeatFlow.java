package dev.deitylamb.yarrow.flows.decorators;

import dev.deitylamb.yarrow.Flow;
import dev.deitylamb.yarrow.common.Displayable;

public class RepeatFlow<T> extends FlowDecorator<T> {

    private final int times;
    private int repeats = 0;

    public RepeatFlow(Flow<T> inner, int times) {
        super(inner);
        this.times = Math.max(-1, times);
    }

    public int repeats() {
        return repeats;
    }

    @Override
    public void tick(T graphics, double delta) {

        if (delta > inner.duration()) {

            this.repeats = Math.min(repeats + (int) Math.floor(delta / inner.duration()), times);
            delta = delta % inner.duration();
        }

        if (inner.isFinished() && !isOver()) {

            this.repeats++;

            inner.restart();
        }

        super.tick(graphics, delta);

    }

    @Override
    public void reset() {
        super.reset();
        this.repeats = 0;
    }

    @Override
    public double alpha() {
        if (isInfinityLoop()) {
            return -1;
        }

        return inner.alpha();
    }

    @Override
    public double duration() {

        if (isInfinityLoop()) {
            return -1;
        }

        return times * inner.duration();
    }

    @Override
    public double elapsed() {

        if (isInfinityLoop()) {
            return -1;
        }

        return inner.duration() * repeats + inner.elapsed();
    }

    private boolean isOver() {
        return !isInfinityLoop() && repeats >= times;
    }

    private boolean isInfinityLoop() {
        return times == -1;
    }

    @Override
    public RepeatFlow<T> speed(double speed) {
        return new RepeatFlow<>(inner.speed(speed), times);
    }

    @Override
    public RepeatFlow<T> then(Flow<T> flow) {
        return new RepeatFlow<>(inner.then(flow), times);
    }

    @Override
    public RepeatFlow<T> clone() {
        return new RepeatFlow<>(inner.clone(), times);
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "RepeatFlow {\n"
                + tab + Displayable.INDENT + "repeats=" + repeats + ",\n"
                + tab + Displayable.INDENT + "times=" + times + ",\n"
                + tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n"
                + tab + "}";
    }

}
