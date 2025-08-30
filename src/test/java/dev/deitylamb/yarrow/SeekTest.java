package dev.deitylamb.yarrow;

import org.junit.jupiter.api.Test;

import dev.deitylamb.yarrow.common.Color;
import dev.deitylamb.yarrow.flows.decorators.RepeatFlow;

class SeekTest {

    @Test
    void seekShouldWork() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        flow.seek(5);
        TestUtils.iter(flow, 5).shouldBe(t -> (5 + t) / flow.duration());

        assert flow.isPaused();

        flow.restart();

        TestUtils.iter(flow, 9).shouldBe(t -> t / 10d);

        assert flow.isRunning();

    }

    @Test
    void seekShouldWorkInSequence() {
        Flow<?> flow = Yarrow.flow(10).circular();
        flow.play();

        flow.seek(10);

        assert flow.isRunning();

        TestUtils.iter(flow, 10).shouldBe(t -> 1d - (t / 10d));

        assert flow.isPaused();

    }

    @Test
    void seekShouldWorkInSeq2() {
        Flow<?> flow = Yarrow.flow(10).then(Yarrow.flow(10)).then(Yarrow.flow(10));
        flow.play();

        assert flow.duration() == 30;

        flow.seek(25);

        assert flow.isRunning();

        TestUtils.iter(flow, 5).shouldBe(t -> (5 + t) / 10d);

        assert flow.isPaused();
    }

    @Test
    void seekShouldStop() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        flow.seek(10);

        assert flow.isPaused();
    }

    @Test
    void seekShouldWorkWithDelay() {
        Flow<?> flow = Yarrow.flow(10).delay(5);
        flow.play();

        flow.seek(3);

        assert flow.isRunning();

        TestUtils.iter(flow, 2).shouldBe(0);
        TestUtils.iter(flow, 10).shouldBe(t -> (t) / 10d);

        assert flow.isPaused();

        flow.restart();

        flow.seek(7);
        assert flow.isRunning();

        TestUtils.iter(flow, 8).shouldBe(t -> (2 + t) / 10d);
        assert flow.isPaused();

        flow.restart();

        flow.seek(flow.duration());

        assert flow.isPaused();
    }

    @Test
    void seekShouldWorkWithRepeat() {
        RepeatFlow<?> flow = Yarrow.flow(10).repeat(5);
        flow.play();

        System.out.println(Color.fromRGBA("#010106Fa"));

        flow.seek(32);

        System.out.println(flow);

        assert flow.repeats() == 3;

        assert flow.alpha() == 0.2;

    }

}
