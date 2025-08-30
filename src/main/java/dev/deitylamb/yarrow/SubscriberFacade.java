package dev.deitylamb.yarrow;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import dev.deitylamb.yarrow.flows.decorators.PubFlow;
import dev.deitylamb.yarrow.flows.hooks.FlowSubscriber;

interface SubscriberFacade<T> {

    PubFlow<T> subscribe(FlowSubscriber<T> hook);

    default PubFlow<T> onPlay(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onPlay(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onPlay(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onPlay() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onPause(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onPause(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onPause(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onPause() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onReset(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onReset(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onReset(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onReset() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onStop(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onStop(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onStop(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onStop() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onRestart(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onRestart(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onRestart(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onRestart() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onTick(BiConsumer<T, Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onTick(T graphics, Flow<T> flow) {
                hook.accept(graphics, flow);
            }

        });
    }

    default PubFlow<T> onTick(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onTick(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onTick(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onTick() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onProgress(BiConsumer<T, Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onProgress(T graphics, Flow<T> flow) {
                hook.accept(graphics, flow);
            }

        });
    }

    default PubFlow<T> onProgress(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onProgress(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onProgress(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onProgress() {
                hook.run();
            }

        });
    }

    default PubFlow<T> onComplete(BiConsumer<T, Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onComplete(T graphics, Flow<T> flow) {
                hook.accept(graphics, flow);
            }

        });
    }

    default PubFlow<T> onComplete(Consumer<Flow<T>> hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onComplete(Flow<T> flow) {
                hook.accept(flow);
            }

        });
    }

    default PubFlow<T> onComplete(Runnable hook) {
        return this.subscribe(new FlowSubscriber<T>() {

            @Override
            public void onComplete() {
                hook.run();
            }

        });
    }

}
