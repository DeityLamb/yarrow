package dev.deitylamb.fern.common;

public class FernUtils {
  public static double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(val, max));
  }

  public static float clamp(float val, float min, float max) {
    return Math.max(min, Math.min(val, max));
  }

  public static int clamp(int val, int min, int max) {
    return Math.max(min, Math.min(val, max));
  }

  public static long clamp(long val, long min, long max) {
    return Math.max(min, Math.min(val, max));
  }

  public static double positiveModulo(double value, double max) {
    return value < 0 ? modulo(value, max) : clamp(value, 0, max);
  }

  public static double modulo(double value, double max) {
    return ((value % max) + max) % max;
  }

  public static double lerp(double alpha, double from, double to) {
    return (1 - alpha) * from + alpha * to;
  }

  public static int lerp(double alpha, int from, int to) {
    return (int) Math.round(lerp(alpha, (double) from, (double) to));
  }

  public static long lerp(double alpha, long from, long to) {
    return (long) lerp(alpha, (double) from, (double) to);
  }

  public static float lerp(double alpha, float from, float to) {
    return (float) lerp(alpha, (double) from, (double) to);
  }

  public static Color lerp(double alpha, Color from, Color to) {
    return Color.lerp(alpha, from, to);
  }
}
