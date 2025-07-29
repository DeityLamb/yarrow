package dev.deitylamb.fern.tweens;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompoundTween<T> implements Tweenable<T> {

    private final List<Tweenable<T>> transitions = new ArrayList<>();

    public CompoundTween(List<Tweenable<T>> transitions) {
        this.transitions.addAll(transitions);
    }

    CompoundTween() {
    }

    @Override
    public void apply(T graphics, double progress) {

        for (Tweenable<T> transition : transitions) {
            transition.apply(graphics, progress);

        }
    }

    @Override
    public void clear(T graphics) {
        Tweenable.super.clear(graphics);
    }

    public String toString() {
        return "CompoundTween {}";
    }

    public CompoundTween<T> clone() {
        return new CompoundTween<>(
                transitions.stream().map(v -> Tweenable.clone(v)).collect(Collectors.toList()));
    }
}
