package dev.deitylamb.yarrow.flows.hooks;

import dev.deitylamb.yarrow.Flow;

public interface FlowSubscriber<T> {

    default void onPlay(Flow<T> flow) {
    }

    default void onPlay() {
    }

    default void onPause(Flow<T> flow) {
    }

    default void onPause() {
    }

    default void onReset(Flow<T> flow) {
    }

    default void onReset() {
    }

    default void onStop(Flow<T> flow) {
    }

    default void onStop() {
    }

    default void onRestart(Flow<T> flow) {
    }

    default void onRestart() {
    }

    default void onComplete(T graphics, Flow<T> flow) {
    }

    default void onComplete(Flow<T> flow) {
    }

    default void onComplete() {
    }

    default void onTick(T graphics, Flow<T> flow) {
    }

    default void onTick(Flow<T> flow) {
    }

    default void onTick() {
    }

    default void onProgress(T graphics, Flow<T> flow) {
    }

    default void onProgress(Flow<T> flow) {
    }

    default void onProgress() {
    }

}
