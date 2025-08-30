package dev.deitylamb.yarrow.flows.decorators;

import dev.deitylamb.yarrow.Flow;
import dev.deitylamb.yarrow.common.Displayable;

public class RepeatFlow<T> extends FlowDecorator<T> {

    private final int times;
    private int repeats = 1;

    public RepeatFlow(Flow<T> inner, int times) {
        super(inner);
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

    @Override
    public double elapsed() {
        return isInfinityLoop() ? -1 : (inner.duration() * (repeats - 1) + inner.elapsed());
    }

    private boolean isOver() {
        return repeats >= times;
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
