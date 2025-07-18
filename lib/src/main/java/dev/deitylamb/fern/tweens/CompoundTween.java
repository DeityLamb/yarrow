package dev.deitylamb.fern.tweens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompoundTween<T> implements Tween<T> {

    private final List<Tween<T>> transitions = new ArrayList<>();

    CompoundTween(List<Tween<T>> transitions) {
        this.transitions.addAll(transitions);
    }

    CompoundTween() {
    }

    public static <T> CompoundTween<T> from(Tween<T> one) {
        return new CompoundTween<>(Arrays.asList(one));
    }

    public static <T> CompoundTween<T> from(
            Tween<T> one,
            Tween<T> two) {
        return new CompoundTween<>(Arrays.asList(one, two));
    }

    public static <T> CompoundTween<T> from(
            Tween<T> one,
            Tween<T> two,
            Tween<T> three) {
        return new CompoundTween<>(Arrays.asList(one, two, three));
    }

    public static <T> CompoundTween<T> from(Tween<T> one,
            Tween<T> two,
            Tween<T> three,
            Tween<T> four) {
        return new CompoundTween<>(Arrays.asList(one, two, three, four));
    }

    public static <T> CompoundTween<T> from(Tween<T> one,
            Tween<T> two,
            Tween<T> three,
            Tween<T> four,
            Tween<T> five) {
        return new CompoundTween<>(Arrays.asList(one, two, three, four, five));
    }

    @Override
    public void apply(T gui, float progress) {

        for (Tween<T> transition : transitions) {
            transition.apply(gui, progress);
        }
    }

    @Override
    public void clear(T gui) {
        Tween.super.clear(gui);
    }

    public List<Tween<T>> transitions() {
        return this.transitions;
    }

    public String toString() {
        return "CompoundTween {}";
    }

    public CompoundTween<T> clone() {
        return new CompoundTween<>(
                transitions.stream().map(v -> Tween.clone(v)).collect(Collectors.toList()));
    }
}
