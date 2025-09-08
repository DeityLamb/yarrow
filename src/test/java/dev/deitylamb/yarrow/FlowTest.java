package dev.deitylamb.yarrow;

import org.junit.jupiter.api.Test;

class FlowTest {

    @Test
    void flowShouldStopAfterEnd() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        assert flow.isRunning();

        TestUtils.iter(flow, 10).shouldBe(t -> t / 10d);

        assert flow.isPaused();
    }

    @Test
    void flowShouldBeOneAfterEnd() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        assert flow.isRunning();

        TestUtils.iter(flow, 10).shouldBe(t -> t / 10d);

        assert flow.isPaused();

        TestUtils.iter(flow, 6).shouldBe(1d);
    }

    @Test
    void easeShouldWork() {
        Flow<?> flow = Yarrow.flow(10).ease(v -> v * 2d);
        flow.play();

        TestUtils.iter(flow, 10).shouldBe(t -> t / 5d);

        assert flow.isPaused();
    }

    @Test
    void reverseShouldWork() {
        Flow<?> flow = Yarrow.flow(10).reverse();
        flow.play();

        TestUtils.iter(flow, 10).shouldBe(t -> 1d - t / 10d);

        assert flow.isPaused();
    }

    @Test
    void thenShouldWork() {
        Flow<?> flow = Yarrow.flow(5).then(Yarrow.flow(10));
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 5d);

        assert flow.isRunning();

        TestUtils.iter(flow, 10).shouldBe(t -> t / 10d);

        assert flow.isPaused();
    }

    @Test
    void circularShouldWork() {
        Flow<?> flow = Yarrow.flow(5).circular();
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 5d);

        TestUtils.iter(flow, 5).shouldBe(t -> 1d - t / 5d);

        assert flow.isPaused();
    }

    @Test
    void repeatShouldWork() {
        Flow<?> flow = Yarrow.flow(10).repeat(5);
        flow.play();

        for (int i = 0; i < 5; i++) {
            System.out.println(flow);
            TestUtils.iter(flow, 10).shouldBe(t -> t / 10d);
            System.out.println(flow);
        }

        assert flow.isPaused();
    }

    @Test
    void loopShouldWork() {
        Flow<?> flow = Yarrow.flow(10).loop();
        flow.play();

        TestUtils.iter(flow, 100).shouldBe(t -> (t / 10d) % 1d);

        assert flow.isRunning();
    }

    @Test
    void pauseShouldWork() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 10d);

        flow.pause();

        TestUtils.iter(flow, 5).shouldBe(0.5d);

        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> (5 + t) / 10d);

        assert flow.isPaused();
    }

    @Test
    void resetShouldWork() {
        Flow<?> flow = Yarrow.flow(10);
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 10d);

        flow.reset();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 10d);

        assert flow.isRunning();
    }

    @Test
    void durationShouldCalculateCorrectly() {
        Flow<?> flow = Yarrow.flow(7).then(Yarrow.flow(3));

        // 7 + 3
        assert flow.duration() == 10;

        // 10 + 5
        assert flow.delay(5).duration() == 15;

        // 10 * 2
        assert flow.circular().duration() == 20;

        // (10 * 2) + 5
        assert flow.circular().delay(5).duration() == 25;

        // (10 + 5) * 2
        assert flow.delay(5).circular().duration() == 30;

        // ((10 * 2) + 5) * 3
        assert flow.circular().delay(5).repeat(3).duration() == 75;

        // For any flow, if it's an infinite loop, return -1
        assert flow.circular().delay(5).repeat(3).loop().duration() == -1;
    }
}
