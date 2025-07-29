package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.common.Color;
import dev.deitylamb.fern.transitions.Transitionable;
import dev.deitylamb.fern.transitions.decorators.RepeatTransition;

class SeekTest {
    @Test
    void seekShouldWork() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        transition.seek(5);
        TestUtils.iter(transition, 5).shouldBe(t -> (5 + t) / transition.duration());

        assert transition.isPaused();

        transition.restart();

        TestUtils.iter(transition, 9).shouldBe(t -> t / 10d);

        assert transition.isRunning();

    }

    @Test
    void seekShouldWorkInSequence() {
        Transitionable<?> transition = Fern.transition(10).circular();
        transition.play();

        transition.seek(10);

        assert transition.isRunning();

        TestUtils.iter(transition, 10).shouldBe(t -> 1d - (t / 10d));

        assert transition.isPaused();
    }

    @Test
    void seekShouldStop() {
        Transitionable<?> transition = Fern.transition(10);
        transition.play();

        transition.seek(10);

        assert transition.isPaused();
    }

    @Test
    void seekShouldWorkWithDelay() {
        Transitionable<?> transition = Fern.transition(10).delay(5);
        transition.play();

        transition.seek(3);

        assert transition.isRunning();

        TestUtils.iter(transition, 2).shouldBe(0);
        TestUtils.iter(transition, 10).shouldBe(t -> (t) / 10d);

        assert transition.isPaused();

        transition.restart();

        transition.seek(7);
        assert transition.isRunning();

        TestUtils.iter(transition, 8).shouldBe(t -> (2 + t) / 10d);
        assert transition.isPaused();

        transition.restart();

        transition.seek(transition.duration());

        assert transition.isPaused();
    }

    @Test
    void seekShouldWorkWithRepeat() {
        RepeatTransition<?> transition = Fern.transition(10).repeat(5);
        transition.play();

        System.out.println(Color.fromRGBA("#010106Fa"));

        transition.seek(32);

        System.out.println(transition);

        assert transition.repeats() == 3;

        assert transition.alpha() == 0.2;

    }

}
