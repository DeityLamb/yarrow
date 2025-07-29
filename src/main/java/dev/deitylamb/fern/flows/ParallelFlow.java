package dev.deitylamb.fern.flows;

import java.util.List;
import java.util.stream.Collectors;

import dev.deitylamb.fern.Flowable;

public class ParallelFlow<T> implements Flowable<T> {

    private final List<Flowable<T>> flows;

    public ParallelFlow(List<Flowable<T>> flows) {
        this.flows = flows;
    }

    @Override
    public void tick(T graphics, double delta) {
        for (Flowable<T> flow : flows) {
            flow.tick(graphics, delta);

        }
    }

    @Override
    public void clear(T graphics) {
        for (Flowable<T> flow : flows) {
            flow.clear(graphics);
        }
    }

    @Override
    public ParallelFlow<T> clone() {
        return new ParallelFlow<>(this.flows.stream().map(Flowable::clone).collect(Collectors.toList()));
    }
}
