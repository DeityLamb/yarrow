package dev.deitylamb.fern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;
import java.util.function.Supplier;

import dev.deitylamb.fern.transitions.Transitionable;

public class TestUtils {
  public static IterShouldBe iter(Transitionable<?> transition, int ticks) {
    return (Function<Integer, Float> should) -> {
      for (int t = 0; t < ticks; t++) {
        assertEquals(should.apply(t), transition.progress(), 0.01f);

        transition.tick(1.0f);
      }
    };
  }

  @FunctionalInterface
  public static interface IterShouldBe {
    void shouldBe(Function<Integer, Float> should);

    default void shouldBe(Supplier<Float> should) {
      shouldBe((t) -> should.get());
    };

    default void shouldBe(float should) {
      shouldBe((t) -> should);
    };

  }
}
