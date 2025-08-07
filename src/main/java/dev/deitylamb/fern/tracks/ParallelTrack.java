package dev.deitylamb.fern.tracks;

import java.util.List;
import java.util.stream.Collectors;

import dev.deitylamb.fern.Tickable;

public class ParallelTrack<T> implements Tickable<T> {

    private final List<Tickable<T>> flows;

    public ParallelTrack(List<Tickable<T>> flows) {
        this.flows = flows;
    }

    @Override
    public void tick(T graphics, double delta) {
        for (Tickable<T> flow : flows) {
            flow.tick(graphics, delta);

        }
    }

    @Override
    public ParallelTrack<T> clone() {
        return new ParallelTrack<>(this.flows.stream().map(Tickable::clone).collect(Collectors.toList()));
    }
}
