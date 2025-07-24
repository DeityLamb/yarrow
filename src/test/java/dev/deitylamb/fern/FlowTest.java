package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.transitions.Transitionable;

class FlowTest {
  @Test
  <T> void flowParallelShouldWork() {
    Transitionable<T> one = Fern.transition(10);
    Transitionable<T> two = Fern.transition(10);

    Flowable<?> parallel = Fern.parallel(one, two);

    one.play();
    two.play();

    TestUtils.iter(parallel, 10);

    assert one.isPaused();
    assert two.isPaused();
  }

  @Test
  <T> void flowParallelShouldWorkWithPauseWork() {
    Transitionable<T> one = Fern.transition(10);
    Transitionable<T> two = Fern.transition(10);

    Flowable<?> parallel = Fern.parallel(one, two);

    one.play();
    two.play();

    TestUtils.iter(parallel, 5);

    two.pause();

    TestUtils.iter(parallel, 5);

    two.play();

    assert one.isPaused();
    assert two.isRunning();

    TestUtils.iter(parallel, 5);

    assert one.isPaused();
    assert two.isPaused();
  }

  @Test
  void focusShouldWork() {
    Transitionable<String> transition = Fern.transition(10);

    Flowable<String> focus = Fern.focus(transition);

    TestUtils.iter(focus, 20);

    assert transition.isPaused();
  }
}
