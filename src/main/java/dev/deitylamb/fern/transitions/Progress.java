package dev.deitylamb.fern.transitions;

import dev.deitylamb.fern.common.Color;
import dev.deitylamb.fern.common.FernUtils;

public class Progress {

  public static Progress ZERO = new Progress(0d);
  public static Progress ONE = new Progress(1d);

  private final double alpha;

  public Progress(double alpha) {
    this.alpha = alpha;
  }

  public double alpha() {
    return this.alpha;
  }

  public double lerp(double from, double to) {
    return FernUtils.lerp(this.alpha, from, to);
  }

  public double lerp(int from, int to) {
    return FernUtils.lerp(this.alpha, from, to);
  }

  public double lerp(long from, long to) {
    return FernUtils.lerp(this.alpha, from, to);
  }

  public double lerp(float from, float to) {
    return FernUtils.lerp(this.alpha, from, to);
  }

  public Color lerp(Color from, Color to) {
    return Color.lerp(this.alpha, from, to);
  }
}
