package dev.deitylamb.fern;

import java.util.Arrays;
import java.util.List;

import dev.deitylamb.fern.flows.FocusFlow;
import dev.deitylamb.fern.flows.ParallelFlow;
import dev.deitylamb.fern.transitions.Transition;
import dev.deitylamb.fern.transitions.Transitionable;

public class Fern {

    public static <T> Transitionable<T> transition(double duration) {
        return new Transition<>(duration);
    }

    public static <T> FocusFlow<T> focus(Transitionable<T> hover, Transitionable<T> blur) {
        return new FocusFlow<>(hover, blur);
    }

    public static <T> FocusFlow<T> focus(Transitionable<T> transition) {
        return focus(transition, transition.reverse());
    }

    @SafeVarargs
    public static <T> ParallelFlow<T> parallel(Flowable<T>... args) {
        return new ParallelFlow<T>(Arrays.asList(args));
    }

    public static <T> ParallelFlow<T> parallel(List<Flowable<T>> transitions) {
        return new ParallelFlow<T>(transitions);

    }

}
