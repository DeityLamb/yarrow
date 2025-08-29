package dev.deitylamb.yarrow;

import org.junit.jupiter.api.Test;

class DelayTest {

  @Test
  void delayShouldWork() {
    Flow<?> transition = Yarrow.flow(10).delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkBeforeCircular() {
    Flow<?> transition = Yarrow.flow(10).delay(5).circular();
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    TestUtils.iter(transition, 5).shouldBe(1);

    TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkAfterCircular() {
    Flow<?> transition = Yarrow.flow(10).circular().delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkBeforeCircularAndLoop() {
    Flow<?> transition = Yarrow.flow(10).delay(5).circular().loop();
    transition.play();

    for (int i = 0; i < 10; i++) {
      TestUtils.iter(transition, 5).shouldBe(0);

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 5).shouldBe(1);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkAfterCircularAndLoop() {
    Flow<?> transition = Yarrow.flow(10).circular().loop().delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    for (int i = 0; i < 10; i++) {

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkAfterCircularAndBeforeLoop() {
    Flow<?> transition = Yarrow.flow(10).circular().delay(5).loop();
    transition.play();

    for (int i = 0; i < 10; i++) {
      TestUtils.iter(transition, 5).shouldBe(0);

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkWithReset() {
    Flow<?> transition = Yarrow.flow(10).delay(5);

    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 7).shouldBe(t -> t / 10d);

    transition.reset();

    TestUtils.iter(transition, 5).shouldBe(0);
  }

  @Test
  void delayShouldWorkWithPause() {
    Flow<?> transition = Yarrow.flow(10).delay(5);

    transition.play();

    TestUtils.iter(transition, 3).shouldBe(0);

    transition.pause();

    TestUtils.iter(transition, 30).shouldBe(0);

    transition.play();

    TestUtils.iter(transition, 2).shouldBe(0);

    TestUtils.iter(transition, 7).shouldBe(t -> t / 10d);
  }

}
