package dev.deitylamb.yarrow.flows;

import java.util.Arrays;

import dev.deitylamb.yarrow.Flow;
import dev.deitylamb.yarrow.common.Displayable;
import dev.deitylamb.yarrow.common.YarrowUtils;

public class BasicFlow<T> implements Flow<T> {

    private final double duration;
    private double elapsed = 0;
    private boolean running = false;

    public BasicFlow(double duration) {
        this.duration = duration;

    }

    @Override
    public double duration() {
        return duration;
    }

    @Override
    public double elapsed() {
        return elapsed;
    }

    @Override
    public void tick(T graphics, double delta) {

        if (!this.isRunning()) {
            return;
        }

        this.elapsed = YarrowUtils.clamp(elapsed + delta, 0, duration);

        if (isFinished()) {
            this.pause();
        }
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
    public Flow<T> speed(double speed) {
        return new BasicFlow<>(Math.max(0, duration / speed));
    }

    @Override
    public Flow<T> then(Flow<T> flow) {
        return new SequenceFlow<>(Arrays.asList(this.clone(), flow.clone()));
    }

    @Override
    public String display(int depth) {

        String tab = Displayable.indent(depth);

        return "BasicFlow {\n"
                + tab + Displayable.INDENT + "alpha=" + alpha() + ",\n"
                + tab + Displayable.INDENT + "running=" + running + ",\n"
                + tab + Displayable.INDENT + "elapsed=" + elapsed + ",\n"
                + tab + Displayable.INDENT + "duration=" + duration + ",\n"
                + tab + "}";
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public BasicFlow<T> clone() {
        return new BasicFlow<>(duration);
    }

}
