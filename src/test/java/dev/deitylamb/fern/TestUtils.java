package dev.deitylamb.fern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;
import java.util.function.Supplier;

import dev.deitylamb.fern.flows.Flow;

public class TestUtils {
  public static IterShouldBe iter(Flow<?> flow, int ticks) {
    return (Function<Integer, Double> should) -> {
      for (int t = 0; t < ticks; t++) {
        assertEquals(flow.alpha(), should.apply(t), 0.01d);
        flow.tick(1.0d);
      }
    };
  }

  @FunctionalInterface
  public static interface IterShouldBe {
    void shouldBe(Function<Integer, Double> should);

    default void shouldBe(Supplier<Double> should) {
      shouldBe((t) -> should.get());
    };

    default void shouldBe(double should) {
      shouldBe((t) -> should);
    };

  }
}
