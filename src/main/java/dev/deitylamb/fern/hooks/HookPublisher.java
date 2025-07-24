package dev.deitylamb.fern.hooks;

import java.util.ArrayList;
import java.util.List;

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

  public void onProgress(T gui, double progress) {
    this.subscribers.forEach(h -> h.onProgress(gui, progress));
    this.subscribers.forEach(h -> h.onProgress(progress));
  }

  public void onTick(T gui, double delta) {
    this.subscribers.forEach(h -> h.onTick(gui, delta));
    this.subscribers.forEach(h -> h.onTick(delta));
  }

  public void onClear(T gui, double progress) {
    this.subscribers.forEach(h -> h.onClear(gui));
    this.subscribers.forEach(h -> h.onClear());
  }
}
