package dev.deitylamb.yarrow.tracks;

import java.util.List;

import dev.deitylamb.yarrow.Tickable;

public class Parallel<T> implements Tickable<T> {

    private final List<Tickable<T>> flows;

    public Parallel(List<Tickable<T>> flows) {
        this.flows = flows;
    }

    @Override
    public void tick(T graphics, double delta) {
        for (Tickable<T> flow : flows) {
            flow.tick(graphics, delta);

        }
    }
}
