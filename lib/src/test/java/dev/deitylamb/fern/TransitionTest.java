package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.transitions.Transitionable;

class TransitionTest {
    @Test
    void transitionShouldStopAfterEnd() {
        Transitionable<?> transition = Fern.transition(10);
        transition.run();

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 10f);

        assert transition.isPaused();
    }

    @Test
    void reverseShouldWork() {
        Transitionable<?> transition = Fern.transition(10).reverse();
        transition.run();

        TestUtils.iter(transition, 10).shouldBe(t -> 1f - t / 10f);

        assert transition.isPaused();
    }

    @Test
    void chainShouldWork() {
        Transitionable<?> transition = Fern.transition(5).then(Fern.transition(10));
        transition.run();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 5f);

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> t / 10f);

        assert transition.isPaused();
    }

    @Test
    void circularShouldWork() {
        Transitionable<?> transition = Fern.transition(5).circular();
        transition.run();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 5f);

        TestUtils.iter(transition, 5).shouldBe(t -> 1f - t / 5f);

        assert transition.isPaused();
    }

    @Test
    void loopShouldWork() {
        Transitionable<?> transition = Fern.transition(10).loop();
        transition.run();

        TestUtils.iter(transition, 100).shouldBe(t -> (t / 10f) % 1f);

        assert transition.isRunning();
    }

    @Test
    void pauseShouldWork() {
        Transitionable<?> transition = Fern.transition(10);
        transition.run();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10f);

        transition.pause();

        TestUtils.iter(transition, 5).shouldBe(0.5f);

        transition.run();

        TestUtils.iter(transition, 5).shouldBe(t -> (5 + t) / 10f);

        assert transition.isPaused();
    }

    @Test
    void resetShouldWork() {
        Transitionable<?> transition = Fern.transition(10);
        transition.run();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10f);

        transition.reset();

        TestUtils.iter(transition, 5).shouldBe(t -> t / 10f);

        assert transition.isRunning();
    }
}
