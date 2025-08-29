package dev.deitylamb.fern;

import java.util.Arrays;
import java.util.List;

import dev.deitylamb.fern.flows.BasicFlow;
import dev.deitylamb.fern.tracks.Focus;
import dev.deitylamb.fern.tracks.Parallel;

public class Fern {

    public static <T> Flow<T> flow(double duration) {
        return new BasicFlow<>(duration);
    }

    public static <T> Focus<T> focus(Flow<T> hover, Flow<T> blur) {
        return new Focus<>(hover, blur);
    }

    public static <T> Focus<T> focus(Flow<T> flow) {
        return focus(flow, flow.reverse());
    }

    @SafeVarargs
    public static <T> Parallel<T> parallel(Tickable<T>... args) {
        return new Parallel<T>(Arrays.asList(args));
    }

    public static <T> Parallel<T> parallel(List<Tickable<T>> tracks) {
        return new Parallel<T>(tracks);

    }

}
