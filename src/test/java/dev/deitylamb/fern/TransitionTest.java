package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.transitions.Transitionable;

class TransitionTest {
    @Test
    void transitionShouldStopAfterEnd() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

        assert transition.isPaused();
    }

    @Test
    void transitionShouldBeOneAfterEnd() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

        assert transition.isPaused();

        TestUtils.iter(transition, 6).shouldBe(1d);
    }

    @Test
    void easeShouldWork() {
        Transitionable<?> transition = Fern.transition(10).ease(v -> v * 2d);
        transition.play();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 5d);

        assert transition.isPaused();
    }

    @Test
    void reverseShouldWork() {
        Transitionable<?> transition = Fern.transition(10).reverse();
        transition.play();

        TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);

        assert transition.isPaused();
    }

    @Test
    void thenShouldWork() {
        Transitionable<?> transition = Fern.transition(5).then(Fern.transition(10));
        transition.play();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 5d);

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

        assert transition.isPaused();
    }

    @Test
    void circularShouldWork() {
        Transitionable<?> transition = Fern.transition(5).circular();
        transition.play();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 5d);

        TestUtils.iter(transition, 5).shouldBe(t -> 1d - t / 5d);

        assert transition.isPaused();
    }

    @Test
    void repeatShouldWork() {
        Transitionable<?> transition = Fern.transition(10).repeat(5);
        transition.play();

        TestUtils.iter(transition, 50).shouldBe(t -> (t / 10d) % 1d);

        assert transition.isPaused();
    }

    @Test
    void loopShouldWork() {
        Transitionable<?> transition = Fern.transition(10).loop();
        transition.play();

        TestUtils.iter(transition, 100).shouldBe(t -> (t / 10d) % 1d);

        assert transition.isRunning();
    }

    @Test
    void pauseShouldWork() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10d);

        transition.pause();

        TestUtils.iter(transition, 5).shouldBe(0.5d);

        transition.play();

        TestUtils.iter(transition, 5).shouldBe(t -> (5 + t) / 10d);

        assert transition.isPaused();
    }

    @Test
    void resetShouldWork() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10d);

        transition.reset();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10d);

        assert transition.isRunning();
    }

    @Test
    void durationShouldCalculateCorrectly() {
        Transitionable<?> transition = Fern.transition(7).then(Fern.transition(3));

        // 7 + 3
        assert transition.duration() == 10;

        // 10 + 5
        assert transition.delay(5).duration() == 15;

        // 10 * 2
        assert transition.circular().duration() == 20;

        // (10 * 2) + 5
        assert transition.circular().delay(5).duration() == 25;

        // (10 + 5) * 2
        assert transition.delay(5).circular().duration() == 30;

        // ((10 * 2) + 5) * 3
        assert transition.circular().delay(5).repeat(3).duration() == 75;

        // For any transition, if it's an infinite loop, return -1
        assert transition.circular().delay(5).repeat(3).loop().duration() == -1;
    }
}
