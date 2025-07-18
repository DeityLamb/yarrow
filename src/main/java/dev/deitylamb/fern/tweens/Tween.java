package dev.deitylamb.fern.tweens;

@FunctionalInterface
public interface Tween<T> {
    void apply(T gui, float alpha);

    default void clear(T gui) {
    };

    @SuppressWarnings("unchecked")
    public static <T> Tween<T> clone(Tween<T> tween) {
        try {
            return (Tween<T>) tween.getClass().getMethod("clone").invoke(tween);
        } catch (Exception exception) {
            return tween;
        }
    }

    public static <T> Tween<T> empty() {
        return (gui, alpha) -> {
        };
    }
}
