package dev.deitylamb.fern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EasingTest {
  @Test
  void blendShouldBlendEasings() {
    assertEquals(0.5f, Easings.blend(Easings::linear, Easings::linear).apply(0.5f));
    assertEquals(0, Easings.blend(Easings::linear, (alpha) -> 1f - alpha).apply(1f));
    assertEquals(1, Easings.blend(Easings::linear, (alpha) -> 1f - alpha).apply(0f));
  }
}
