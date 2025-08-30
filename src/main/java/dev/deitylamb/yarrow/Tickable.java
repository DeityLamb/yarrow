package dev.deitylamb.yarrow;

public interface Tickable<T> {

    void tick(T graphics, double delta);

    default void tick(double delta) {
        this.tick(null, delta);
    }

}
