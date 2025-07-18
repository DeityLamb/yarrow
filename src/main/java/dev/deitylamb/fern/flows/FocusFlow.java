package dev.deitylamb.fern.flows;

import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.transitions.Transitionable;

public class FocusFlow<T> implements Flowable<T> {
    private final Transitionable<T> hover;
    private final Transitionable<T> blur;
    private boolean isFocused = false;
    private boolean wasFocused = false;

    public FocusFlow(Transitionable<T> hover, Transitionable<T> blur) {
        this.hover = hover;
        this.blur = blur;
    }

    public void setFocus(boolean isFocused) {
        this.isFocused = isFocused;
    }

    @Override
    public void tick(T gui, float delta) {

        if (isFocused && wasFocused) {

            if (!hover.isRunning()) {
                hover.apply(gui, 1f);
                return;
            }

            hoverTick(gui, delta);
            return;
        }

        if (!isFocused && wasFocused) {
            this.blurTick(gui, delta);
            wasFocused = this.blur.isRunning();
            return;
        }

        if (isFocused && !wasFocused) {
            this.hoverTick(gui, delta);
            this.wasFocused = isFocused;
            return;
        }
    }

    @Override
    public void clear(T gui) {
        hover.clear(gui);
        blur.clear(gui);
    }

    private void hoverTick(T gui, float delta) {

        if (blur.isRunning()) {
            blur.stop();
        }

        if (!hover.isRunning()) {
            hover.restart();
        }

        hover.tick(gui, delta);
    }

    private void blurTick(T gui, float delta) {
        if (hover.isRunning()) {
            hover.stop();
        }

        if (!blur.isRunning()) {
            blur.restart();
        }

        blur.tick(gui, delta);
    }

    @Override
    public FocusFlow<T> clone() {
        return new FocusFlow<>(hover.clone(), blur.clone());
    }
}
