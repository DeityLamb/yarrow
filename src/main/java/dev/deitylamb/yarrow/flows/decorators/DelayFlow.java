package dev.deitylamb.yarrow.flows.decorators;

import java.util.Arrays;

import dev.deitylamb.yarrow.Flow;
import dev.deitylamb.yarrow.common.Displayable;
import dev.deitylamb.yarrow.common.YarrowUtils;
import dev.deitylamb.yarrow.flows.SequenceFlow;

public class DelayFlow<T> extends FlowDecorator<T> {
    public final double delay;
    private double elapsed = 0;

    public DelayFlow(Flow<T> inner, double delay) {
        super(inner);
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
            inner.tick(graphics, 0d);
            this.elapsed = YarrowUtils.clamp(elapsed + delta, 0, delay);
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
    public DelayFlow<T> speed(double speed) {
        return new DelayFlow<>(inner.speed(speed), delay / speed);
    }

    @Override
    public SequenceFlow<T> then(Flow<T> flow) {
        return new SequenceFlow<>(Arrays.asList(this.clone(), flow.clone()));
    }

    @Override
    public DelayFlow<T> clone() {
        return new DelayFlow<>(inner.clone(), delay);
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "DelayFlow {\n" +
                tab + Displayable.INDENT + "elapsed=" + elapsed + ",\n" +
                tab + Displayable.INDENT + "delay=" + delay + ",\n" +
                tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
                tab + "}";
    }
}
