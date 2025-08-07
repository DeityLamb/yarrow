package dev.deitylamb.fern;

import java.util.Arrays;
import java.util.List;

import dev.deitylamb.fern.flows.BasicFlow;
import dev.deitylamb.fern.flows.Flow;
import dev.deitylamb.fern.tracks.FocusTrack;
import dev.deitylamb.fern.tracks.ParallelTrack;

public class Fern {

    public static <T> Flow<T> flow(double duration) {
        return new BasicFlow<>(duration);
    }

    public static <T> FocusTrack<T> focus(Flow<T> hover, Flow<T> blur) {
        return new FocusTrack<>(hover, blur);
    }

    public static <T> FocusTrack<T> focus(Flow<T> flow) {
        return focus(flow, flow.reverse());
    }

    @SafeVarargs
    public static <T> ParallelTrack<T> parallel(Tickable<T>... args) {
        return new ParallelTrack<T>(Arrays.asList(args));
    }

    public static <T> ParallelTrack<T> parallel(List<Tickable<T>> tracks) {
        return new ParallelTrack<T>(tracks);

    }

}
