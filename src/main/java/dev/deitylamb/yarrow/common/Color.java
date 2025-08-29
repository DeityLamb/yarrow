package dev.deitylamb.yarrow.common;

public class Color implements Displayable {

  public static final Color BLACK = Color.fromRGB(0, 0, 0);
  public static final Color WHITE = Color.fromRGB(255, 255, 255);
  public static final Color RED = Color.fromRGB(255, 0, 0);
  public static final Color GREEN = Color.fromRGB(0, 255, 0);
  public static final Color BLUE = Color.fromRGB(0, 0, 255);

  private final int argb;

  private Color(int argb) {
    this.argb = argb;
  }

  public static Color fromARGB(int argb) {
    return new Color(argb);
  }

  public static Color fromARGB(int a, int r, int g, int b) {
    return new Color((a << 24) | (r << 16) | (g << 8) | b);
  }

  public static Color fromARGB(String hex) {
    String cleaned = hex.replace("#", "");
    if (cleaned.length() != 8) {
      throw new IllegalArgumentException("Expected ARGB hex string (8 characters): " + hex);
    }
    int value = (int) Long.parseUnsignedLong(cleaned, 16);
    return new Color(value);
  }

  public static Color fromRGBA(int rgba) {
    int r = (rgba >> 24) & 0xFF;
    int g = (rgba >> 16) & 0xFF;
    int b = (rgba >> 8) & 0xFF;
    int a = rgba & 0xFF;
    return fromARGB(a, r, g, b);
  }

  public static Color fromRGBA(int r, int g, int b, int a) {
    return fromARGB(a, r, g, b);
  }

  public static Color fromRGBA(String hex) {
    String cleaned = hex.replace("#", "");
    if (cleaned.length() != 8) {
      throw new IllegalArgumentException("Expected RGBA hex string (8 characters): " + hex);
    }

    int rgba = (int) Long.parseUnsignedLong(cleaned, 16);
    int r = (rgba >> 24) & 0xFF;
    int g = (rgba >> 16) & 0xFF;
    int b = (rgba >> 8) & 0xFF;
    int a = rgba & 0xFF;

    return fromARGB(a, r, g, b);
  }

  public static Color fromRGB(int rgb) {
    return new Color(0xFF000000 | (rgb & 0x00FFFFFF));
  }

  public static Color fromRGB(int r, int g, int b) {
    return fromARGB(255, r, g, b);
  }

  public static Color fromRGB(String hex) {
    String cleaned = hex.replace("#", "");
    if (cleaned.length() != 6) {
      throw new IllegalArgumentException("Expected RGB hex string (6 characters): " + hex);
    }

    int rgb = (int) Long.parseUnsignedLong(cleaned, 16);
    return new Color(0xFF000000 | rgb);
  }

  public static Color lerp(float t, Color one, Color two) {
    int a = Math.round(one.alpha() + (two.alpha() - one.alpha()) * t);
    int r = Math.round(one.red() + (two.red() - one.red()) * t);
    int g = Math.round(one.green() + (two.green() - one.green()) * t);
    int b = Math.round(one.blue() + (two.blue() - one.blue()) * t);
    return fromARGB(a, r, g, b);
  }

  public static Color lerp(double t, Color one, Color two) {
    return lerp((float) YarrowUtils.clamp(t, 0, 1), one, two);
  }

  public int argb() {
    return this.argb;
  }

  public int rgba() {
    return (red() << 24) | (green() << 16) | (blue() << 8) | alpha();
  }

  public int rgb() {
    return (red() << 16) | (green() << 8) | blue();
  }

  public int alpha() {
    return (argb >> 24) & 0xFF;
  }

  public int red() {
    return (argb >> 16) & 0xFF;
  }

  public int green() {
    return (argb >> 8) & 0xFF;
  }

  public int blue() {
    return argb & 0xFF;
  }

  public String hex() {
    return String.format("#%08X", argb);
  }

  public Color withRed(int red) {
    return fromARGB(alpha(), red, green(), blue());
  }

  public Color withGreen(int green) {
    return fromARGB(alpha(), red(), green, blue());
  }

  public Color withBlue(int blue) {
    return fromARGB(alpha(), red(), green(), blue);
  }

  public Color withAlpha(int alpha) {
    return fromARGB(alpha, red(), green(), blue());
  }

  public Color withOpacity(double opacity) {
    return fromARGB(
        (int) YarrowUtils.clamp(opacity * (double) 255, 0, 255),
        red(),
        green(),
        blue());
  }

  @Override
  public String toString() {
    return display();
  }

  @Override
  public String display(int depth) {
    String tab = Displayable.indent(depth);
    return "Color {\n" +
        tab + Displayable.INDENT + "rgba=" + "rgba(" +
        red() + ", " +
        green() + ", " +
        blue() + ", " +
        alpha() + ")\n" +
        tab + "}";
  }

}
