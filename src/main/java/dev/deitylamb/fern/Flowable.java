package dev.deitylamb.fern;

public interface Flowable<T> {

    void tick(T graphics, double delta);

    default void tick(double delta) {
        this.tick(null, delta);
    }

    Flowable<T> clone();

}
