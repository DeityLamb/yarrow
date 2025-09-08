package dev.deitylamb.yarrow;

import org.junit.jupiter.api.Test;

public class SpeedTest {

    @Test
    void speedShouldWork() {
        Flow<?> flow = Yarrow.flow(10).speed(2);
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 5d);

        assert flow.isPaused();
    }

    @Test
    void speedShouldWorkWithDelay() {
        Flow<?> flow = Yarrow.flow(20).delay(10).speed(2);
        flow.play();

        TestUtils.iter(flow, 5).shouldBe(0);

        TestUtils.iter(flow, 10).shouldBe(t -> t / 10d);

        assert flow.isPaused();
    }

    @Test
    void speedShouldWorkWithRepeat() {
        Flow<?> flow = Yarrow.flow(10).repeat(5).speed(2);
        flow.play();

        TestUtils.iter(flow, 25).shouldBe(t -> (t / 5d) % 1d);

        System.out.println(flow);
        assert flow.isPaused();
    }

    @Test
    void speedShouldWorkInSequence() {
        Flow<?> flow = Yarrow
                .flow(10)
                .speed(2);

        flow.play();

        TestUtils.iter(flow, 5).shouldBe(t -> t / 5d);

        assert flow.isPaused();
    }
}
