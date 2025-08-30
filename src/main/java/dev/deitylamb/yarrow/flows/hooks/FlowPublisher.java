package dev.deitylamb.yarrow.flows.hooks;

import java.util.ArrayList;
import java.util.List;

import dev.deitylamb.yarrow.Flow;

public class FlowPublisher<T> {

    private final List<FlowSubscriber<T>> subscribers = new ArrayList<>();

    public FlowPublisher() {
    }

    public FlowPublisher<T> subscribe(FlowSubscriber<T> hook) {
        this.subscribers.add(hook);
        return this;
    }

    public FlowPublisher<T> subscribe(List<FlowSubscriber<T>> hooks) {
        this.subscribers.addAll(hooks);
        return this;
    }

    public void emitPlay(Flow<T> flow) {
        this.subscribers.forEach((sub) -> {
            sub.onPlay(flow);
            sub.onPlay();
        });
    }

    public void emitPause(Flow<T> flow) {
        this.subscribers.forEach((sub) -> {
            sub.onPause(flow);
            sub.onPause();
        });
    }

    public void emitReset(Flow<T> flow) {
        this.subscribers.forEach((sub) -> {
            sub.onReset(flow);
            sub.onReset();
        });
    }

    public void emitStop(Flow<T> flow) {
        this.subscribers.forEach((sub) -> {
            sub.onStop(flow);
            sub.onStop();
        });
    }

    public void emitRestart(Flow<T> flow) {
        this.subscribers.forEach((sub) -> {
            sub.onRestart(flow);
            sub.onRestart();
        });
    }

    public void emitComplete(T graphics, Flow<T> flow) {
        this.subscribers.forEach(sub -> {
            sub.onComplete(graphics, flow);
            sub.onComplete(flow);
            sub.onComplete();
        });
    }

    public void emitProgress(T graphics, Flow<T> flow) {
        this.subscribers.forEach(sub -> {
            sub.onProgress(graphics, flow);
            sub.onProgress(flow);
            sub.onProgress();
        });
    }

    public void emitTick(T graphics, Flow<T> flow) {
        this.subscribers.forEach(sub -> {
            sub.onTick(graphics, flow);
            sub.onTick(flow);
            sub.onTick();
        });
    }
}
