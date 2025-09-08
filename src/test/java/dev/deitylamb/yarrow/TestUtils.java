package dev.deitylamb.yarrow;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static IterShouldBe iter(Flow<?> flow, int ticks) {
        return (Function<Integer, Double> should) -> {
            for (int t = 0; t < ticks; t++) {
                assertEquals(should.apply(t), flow.alpha(), 0.001d);
                flow.tick(1.0d);
            }
        };
    }

    @FunctionalInterface
    public static interface IterShouldBe {

        void shouldBe(Function<Integer, Double> should);

        default void shouldBe(Supplier<Double> should) {
            shouldBe((t) -> should.get());
        }

        ;

    default void shouldBe(double should) {
            shouldBe((t) -> should);
        }
    ;

}
}
