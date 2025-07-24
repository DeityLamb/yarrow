package dev.deitylamb.fern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.common.Easings;

class EasingTest {
  @Test
  void blendShouldBlendEasings() {
    assertEquals(0.5d, Easings.blend(Easings::linear, Easings::linear).apply(0.5d));
    assertEquals(0, Easings.blend(Easings::linear, (alpha) -> 1d - alpha).apply(1d));
    assertEquals(1, Easings.blend(Easings::linear, (alpha) -> 1d - alpha).apply(0d));
  }
}
