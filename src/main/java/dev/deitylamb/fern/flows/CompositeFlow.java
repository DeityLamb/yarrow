package dev.deitylamb.fern.flows;

import java.util.List;
import java.util.stream.Collectors;

import dev.deitylamb.fern.Flowable;

public class CompositeFlow<T> implements Flowable<T> {

    private final List<Flowable<T>> flows;

    public CompositeFlow(List<Flowable<T>> flows) {
        this.flows = flows;
    }

    @Override
    public void tick(T gui, float delta) {
        for (Flowable<T> flow : flows) {
            flow.tick(gui, delta);
        }
    }

    @Override
    public void clear(T gui) {
        for (Flowable<T> flow : flows) {
            flow.clear(gui);
        }
    }

    @Override
    public CompositeFlow<T> clone() {
        return new CompositeFlow<>(this.flows.stream().map(Flowable::clone).collect(Collectors.toList()));
    }
}
