package dev.deitylamb.fern;

public interface Flowable<T> {

    void tick(T gui, float delta);

    default void tick(float delta) {
        this.tick(null, delta);
    }

    void clear(T gui);

    Flowable<T> clone();

}
