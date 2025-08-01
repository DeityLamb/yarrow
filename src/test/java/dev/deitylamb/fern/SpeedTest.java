package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.transitions.Transitionable;

public class SpeedTest {

  @Test
  void speedShouldWork() {
    Transitionable<?> transition = Fern.transition(10).speed(2);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(t -> t / 5d);

    assert transition.isPaused();
  }

  @Test
  void speedShouldWorkWithDelay() {
    Transitionable<?> transition = Fern.transition(20).delay(10).speed(2);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    assert transition.isPaused();
  }

  @Test
  void speedShouldWorkWithRepeat() {
    Transitionable<?> transition = Fern.transition(10).repeat(5).speed(2);
    transition.play();

    TestUtils.iter(transition, 25).shouldBe(t -> (t / 5d) % 1d);

    assert transition.isPaused();
  }

  @Test
  void speedShouldWorkInSequence() {
    Transitionable<?> transition = Fern
        .transition(10)
        .speed(2);

    transition.play();

    TestUtils.iter(transition, 5).shouldBe(t -> t / 5d);

    assert transition.isPaused();
  }
}
