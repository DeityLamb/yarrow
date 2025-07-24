package dev.deitylamb.fern;

public interface Flowable<T> {

    void tick(T gui, double delta);

    default void tick(double delta) {
        this.tick(null, delta);
    }

    void clear(T gui);

    Flowable<T> clone();

}
