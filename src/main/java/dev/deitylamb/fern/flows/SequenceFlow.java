package dev.deitylamb.fern.flows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;

public class SequenceFlow<T> implements Flow<T> {
    private List<Flow<T>> edges;
    private Optional<Flow<T>> active;

    public SequenceFlow(List<Flow<T>> edges) {
        this.edges = edges;
        this.active = first();
    }

    @Override
    public double duration() {
        return edges.stream().mapToDouble(Flow::duration).sum();
    }

    @Override
    public double alpha() {
        return active.map(v -> v.alpha()).orElse(0d);
    }

    @Override
    public void tick(T graphics, double delta) {
        boolean run = isRunning();

        this.active.ifPresent(v -> v.tick(graphics, delta));

        if (run && !isRunning()) {
            this.active.ifPresent(v -> v.reset());
            this.active = next();
            this.active.ifPresent(v -> v.play());
        }
    }

    @Override
    public boolean isRunning() {
        return this.active.map(v -> v.isRunning()).orElse(false);
    }

    @Override
    public void play() {
        this.active.ifPresent(v -> v.play());
    }

    @Override
    public void reset() {
        boolean run = isRunning();

        this.active.ifPresent(v -> v.stop());

        this.active = first();

        if (run) {
            this.active.ifPresent(v -> v.play());
        }
    }

    @Override
    public void pause() {
        this.active.ifPresent(v -> v.pause());
    }

    @Override
    public SequenceFlow<T> then(Flow<T> flow) {
        List<Flow<T>> edges = new ArrayList<>(
                this.edges.stream().map(Flow::clone).collect(Collectors.toList()));

        edges.add(flow.clone());

        return new SequenceFlow<>(edges);
    }

    @Override
    public SequenceFlow<T> speed(double speed) {
        return map(v -> v.speed(speed));
    }

    @Override
    public SequenceFlow<T> ease(Ease ease) {
        return map(v -> v.ease(ease));
    }

    @Override
    public SequenceFlow<T> clone() {
        return map(Flow::clone);
    }

    private SequenceFlow<T> map(Function<Flow<T>, Flow<T>> mapper) {
        return new SequenceFlow<>(new ArrayList<>(
                this.edges.stream().map(mapper).collect(Collectors.toList())));
    }

    private Optional<Flow<T>> next() {
        if (!active.isPresent()) {
            return Optional.empty();
        }

        int currentIdx = this.edges.indexOf(active.get());

        if (currentIdx == -1 || (currentIdx + 1) >= edges.size()) {
            return Optional.empty();
        }

        return Optional.of(edges.get(currentIdx + 1));
    }

    private Optional<Flow<T>> first() {
        if (edges.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(edges.get(0));
    }

    @Override
    public String display(int depth) {

        String indents = Displayable.indent(depth);

        return "SequenceFlow {\n" +
                indents + Displayable.INDENT + "edges=[\n" +

                indents + Displayable.indent(2) + edges
                        .stream()
                        .map(v -> v.display(depth + 2))
                        .collect(Collectors.joining(",\n" + indents + Displayable.indent(2)))
                + "\n" +
                indents + Displayable.INDENT + "]\n" +
                indents + "}";
    }

    @Override
    public String toString() {
        return display();
    }
}
