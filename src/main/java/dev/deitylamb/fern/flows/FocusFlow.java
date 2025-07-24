package dev.deitylamb.fern.flows;

import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.transitions.Transitionable;

public class FocusFlow<T> implements Flowable<T> {
    private final Transitionable<T> focus;
    private final Transitionable<T> blur;
    private boolean isFocused = false;
    private boolean wasFocused = false;

    public FocusFlow(Transitionable<T> focus, Transitionable<T> blur) {
        this.focus = focus;
        this.blur = blur;
    }

    public void setFocus(boolean isFocused) {
        this.isFocused = isFocused;
    }

    @Override
    public void tick(T gui, double delta) {

        if (isFocused && wasFocused) {

            if (!focus.isRunning()) {
                focus.apply(gui, 1d);
                return;
            }

            focusTick(gui, delta);
            return;
        }

        if (!isFocused && wasFocused) {
            this.blurTick(gui, delta);
            wasFocused = this.blur.isRunning();
            return;
        }

        if (isFocused && !wasFocused) {
            this.focusTick(gui, delta);
            this.wasFocused = isFocused;
            return;
        }
    }

    @Override
    public void clear(T gui) {
        focus.clear(gui);
        blur.clear(gui);
    }

    private void focusTick(T gui, double delta) {

        if (blur.isRunning()) {
            blur.stop();
        }

        if (!focus.isRunning()) {
            focus.restart();
        }

        focus.tick(gui, delta);
    }

    private void blurTick(T gui, double delta) {
        if (focus.isRunning()) {
            focus.stop();
        }

        if (!blur.isRunning()) {
            blur.restart();
        }

        blur.tick(gui, delta);
    }

    @Override
    public FocusFlow<T> clone() {
        return new FocusFlow<>(focus.clone(), blur.clone());
    }
}
