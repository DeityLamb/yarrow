package dev.deitylamb.fern.hooks;

import java.util.function.Function;

public class HookBuilder<T> {

    public static interface HookFactory<T> extends Function<HookBuilder<T>, HookBuilder<T>> {
    }

    private HookPublisher<T> hooks = new HookPublisher<>();

    public static <T> HookBuilder<T> builder() {
        return new HookBuilder<>();
    }

    public HookBuilder<T> subscribe(HookSubscriber<T> hook) {
        this.hooks.subscribe(hook);
        return this;
    }

    public HookBuilder<T> onPlay(HookSubscriber.OnPlay hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }

    public HookBuilder<T> onPause(HookSubscriber.OnPause hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }

    HookBuilder<T> onProgress(HookSubscriber.OnProgress<T> hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }

    HookBuilder<T> onProgress(HookSubscriber.OnProgressNoGui hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }

    public HookBuilder<T> onTick(HookSubscriber.OnTick<T> hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }

    public HookBuilder<T> onTick(HookSubscriber.OnTickNoGui hook) {
        this.hooks.subscribe(hook.asHookable());
        return this;
    }
}
