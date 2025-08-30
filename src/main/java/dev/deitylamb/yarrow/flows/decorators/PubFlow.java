package dev.deitylamb.yarrow.flows.decorators;

import java.util.Arrays;

import dev.deitylamb.yarrow.Flow;
import dev.deitylamb.yarrow.common.Displayable;
import dev.deitylamb.yarrow.flows.SequenceFlow;
import dev.deitylamb.yarrow.flows.hooks.FlowPublisher;
import dev.deitylamb.yarrow.flows.hooks.FlowSubscriber;

public class PubFlow<T> extends FlowDecorator<T> {

    private final FlowPublisher<T> publisher = new FlowPublisher<>();

    public PubFlow(Flow<T> inner) {
        super(inner);
    }

    @Override
    public void tick(T graphics, double delta) {

        boolean run = isRunning();

        inner.tick(graphics, delta);
        publisher.emitTick(graphics, this);

        if (run) {
            publisher.emitProgress(graphics, this);
        }

        if (run && !isRunning()) {
            publisher.emitComplete(graphics, this);
        }
    }

    @Override
    public void pause() {
        super.pause();
        publisher.emitPause(this);
    }

    @Override
    public void play() {
        super.play();
        publisher.emitPlay(this);
    }

    @Override
    public void reset() {
        super.reset();
        publisher.emitReset(this);
    }

    @Override
    public void stop() {
        super.stop();
        publisher.emitStop(this);
    }

    @Override
    public void restart() {
        super.restart();
        publisher.emitRestart(this);
    }

    @Override
    public FlowDecorator<T> clone() {
        return new PubFlow<>(inner.clone());
    }

    @Override
    public PubFlow<T> subscribe(FlowSubscriber<T> hook) {
        this.publisher.subscribe(hook);
        return this;
    }

    @Override
    public SequenceFlow<T> then(Flow<T> flow) {
        return new SequenceFlow<>(Arrays.asList(this.clone(), flow.clone()));
    }

    @Override
    public PubFlow<T> speed(double speed) {
        return new PubFlow<>(inner.speed(speed));
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {
        String tab = Displayable.indent(depth);

        return "PubFlow {\n"
                + tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n"
                + tab + "}";
    }
}
