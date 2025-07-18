package dev.deitylamb.fern.transitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dev.deitylamb.fern.Formattable;

public class ChainTransition<T> implements Transitionable<T> {
    private List<Transitionable<T>> edges;
    private Optional<Transitionable<T>> active;

    public ChainTransition(List<Transitionable<T>> edges) {
        this.edges = edges;
        this.active = first();
    }

    @Override
    public float progress() {
        return active.map(v -> v.progress()).orElse(0f);
    }

    @Override
    public void apply(T gui, float alpha) {
        this.active.ifPresent(v -> v.apply(gui, alpha));
    }

    @Override
    public void tick(T gui, float delta) {
        boolean run = isRunning();

        this.active.ifPresent(v -> v.tick(gui, delta));

        if (run && !isRunning()) {
            this.active = next();
            this.active.ifPresent(v -> v.run());
        }
    }

    @Override
    public void clear(T gui) {
        this.active.ifPresent(v -> v.clear(gui));
    }

    @Override
    public boolean isRunning() {
        return this.active.map(v -> v.isRunning()).orElse(false);
    }

    @Override
    public void run() {
        this.active.ifPresent(v -> v.run());
    }

    @Override
    public void reset() {
        boolean run = isRunning();

        this.active.ifPresent(v -> v.stop());

        this.active = first();

        if (run) {
            this.active.ifPresent(v -> v.run());
        }
    }

    @Override
    public void pause() {
        this.active.ifPresent(v -> v.pause());
    }

    @Override
    public ChainTransition<T> chain(Transitionable<T> transition) {
        List<Transitionable<T>> edges = new ArrayList<>(
                this.edges.stream().map(Transitionable::clone).collect(Collectors.toList()));

        edges.add(transition.clone());

        return new ChainTransition<>(edges);
    }

    @Override
    public ChainTransition<T> reverse() {
        return new ChainTransition<>(new ArrayList<>(
                this.edges.stream().map(Transitionable::reverse).collect(Collectors.toList())));
    }

    private Optional<Transitionable<T>> next() {
        if (!active.isPresent()) {
            return Optional.empty();
        }

        int currentIdx = this.edges.indexOf(active.get());

        if (currentIdx == -1 || (currentIdx + 1) >= edges.size()) {
            return Optional.empty();
        }

        return Optional.of(edges.get(currentIdx + 1));
    }

    private Optional<Transitionable<T>> first() {
        if (edges.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(edges.get(0));
    }

    @Override
    public ChainTransition<T> clone() {
        return new ChainTransition<>(this.edges.stream().map(Transitionable::clone).collect(Collectors.toList()));
    }

    @Override
    public String display(int depth) {

        String tab = Formattable.indent(depth);

        return "ChainTransition [\n" +
                tab + Formattable.INDENT +
                edges
                        .stream()
                        .map(v -> v.display(depth + 1))
                        .collect(Collectors.joining(",\n" + tab + Formattable.INDENT))
                + "\n" +
                tab + "}]";
    }

    @Override
    public String toString() {
        return display();
    }
}
