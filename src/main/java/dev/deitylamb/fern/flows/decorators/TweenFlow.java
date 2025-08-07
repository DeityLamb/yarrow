package dev.deitylamb.fern.flows.decorators;

import java.util.Arrays;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.flows.Flow;
import dev.deitylamb.fern.flows.SequenceFlow;
import dev.deitylamb.fern.hooks.HookPublisher;

public class TweenFlow<T> extends FlowDecorator<T> {

  private final HookPublisher<T> publisher;

  public TweenFlow(Flow<T> inner, HookPublisher<T> publisher) {
    super(inner);
    this.publisher = publisher;
  }

  @Override
  public void tick(T graphics, double delta) {

    boolean run = isRunning();

    inner.tick(graphics, delta);
    publisher.onTick(graphics, this);

    if (run) {
      publisher.onProgress(graphics, this);
    }
  }

  @Override
  public void pause() {
    super.pause();
    publisher.onPause();
  }

  @Override
  public void play() {
    super.play();
    publisher.onPlay();
  }

  @Override
  public void reset() {
    super.reset();
    publisher.onReset();
  }

  @Override
  public void stop() {
    super.stop();
    publisher.onStop();
  }

  @Override
  public void restart() {
    super.restart();
    publisher.onRestart();
  }

  @Override
  public FlowDecorator<T> clone() {
    return new TweenFlow<>(inner.clone(), publisher);
  }

  @Override
  public SequenceFlow<T> then(Flow<T> flow) {
    return new SequenceFlow<>(Arrays.asList(this.clone(), flow.clone()));
  }

  @Override
  public TweenFlow<T> speed(double speed) {
    return new TweenFlow<>(inner.speed(speed), publisher);
  }

  @Override
  public String toString() {
    return display();
  }

  @Override
  public String display(int depth) {
    String tab = Displayable.indent(depth);

    return "TweenFlow {\n" +
        tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
        tab + "}";
  }
}
