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
    public void tick(T graphics, double delta) {

        if (isFocused && wasFocused) {

            if (!focus.isRunning()) {
                focus.apply(graphics, 1d);
                return;
            }

            focusTick(graphics, delta);
            return;

        }

        if (!isFocused && wasFocused) {
            this.blurTick(graphics, delta);
            wasFocused = this.blur.isRunning();
            return;

        }

        if (isFocused && !wasFocused) {
            this.focusTick(graphics, delta);
            this.wasFocused = isFocused;
            return;

        }
    }

    @Override
    public void clear(T graphics) {
        focus.clear(graphics);
        blur.clear(graphics);
    }

    private void focusTick(T graphics, double delta) {

        if (blur.isRunning()) {
            blur.stop();
        }

        if (!focus.isRunning()) {
            focus.restart();
        }

        focus.tick(graphics, delta);

    }

    private void blurTick(T graphics, double delta) {
        if (focus.isRunning()) {
            focus.stop();
        }

        if (!blur.isRunning()) {
            blur.restart();
        }

        blur.tick(graphics, delta);

    }

    @Override
    public FocusFlow<T> clone() {
        return new FocusFlow<>(focus.clone(), blur.clone());
    }
}
