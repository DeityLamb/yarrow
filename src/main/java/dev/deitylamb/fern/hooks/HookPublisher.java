package dev.deitylamb.fern.hooks;

import java.util.ArrayList;
import java.util.List;

import dev.deitylamb.fern.Flow;

public class HookPublisher<T> {

  private final List<HookSubscriber<T>> subscribers = new ArrayList<>();

  public HookPublisher() {
  }

  public HookPublisher(HookSubscriber<T> hooks) {
    this.subscribers.add(hooks);
  }

  public HookPublisher(List<HookSubscriber<T>> hooks) {
    this.subscribers.addAll(hooks);
  }

  public void subscribe(HookSubscriber<T> hook) {
    this.subscribers.add(hook);
  }

  public void subscribe(List<HookSubscriber<T>> hooks) {
    this.subscribers.addAll(hooks);
  }

  public void onPlay() {
    this.subscribers.forEach(HookSubscriber::onPlay);
  }

  public void onPause() {
    this.subscribers.forEach(HookSubscriber::onPause);
  }

  public void onReset() {
    this.subscribers.forEach(HookSubscriber::onReset);
  }

  public void onStop() {
    this.subscribers.forEach(HookSubscriber::onStop);
  }

  public void onRestart() {
    this.subscribers.forEach(HookSubscriber::onRestart);
  }

  public void onComplete(T graphics, Flow<T> flow) {
    this.subscribers.forEach(h -> h.onComplete(graphics, flow));
    this.subscribers.forEach(h -> h.onComplete(flow));
  }

  public void onProgress(T graphics, Flow<T> flow) {
    this.subscribers.forEach(h -> h.onProgress(graphics, flow));
    this.subscribers.forEach(h -> h.onProgress(flow));
  }

  public void onTick(T graphics, Flow<T> flow) {
    this.subscribers.forEach(h -> h.onTick(graphics, flow));
    this.subscribers.forEach(h -> h.onTick(flow));
  }
}
