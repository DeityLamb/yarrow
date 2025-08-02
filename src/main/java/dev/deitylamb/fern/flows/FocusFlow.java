package dev.deitylamb.fern.flows;

import java.util.Optional;

import dev.deitylamb.fern.Flowable;
import dev.deitylamb.fern.transitions.Transitionable;

public class FocusFlow<T> implements Flowable<T> {
    private final Transitionable<T> focus;
    private final Transitionable<T> blur;
    private boolean isFocused = false;

    public FocusFlow(Transitionable<T> focus, Transitionable<T> blur) {
        this.focus = focus;
        this.blur = blur;
    }

    public Optional<Transitionable<T>> active() {
        if (isFocused) {
            return Optional.of(focus);
        }

        if (!isFocused && blur.isRunning()) {
            return Optional.of(blur);
        }

        return Optional.empty();
    }

    public void setFocus(boolean isFocused) {
        if (this.isFocused != isFocused) {
            this.isFocused = isFocused;

            if (isFocused) {
                blur.stop();
                focus.restart();
            } else {
                focus.stop();
                blur.restart();
            }
        }
    }

    @Override
    public void tick(T graphics, double delta) {
        focus.tick(graphics, delta);
        blur.tick(graphics, delta);
    }

    @Override
    public FocusFlow<T> clone() {
        return new FocusFlow<>(this.focus.clone(), this.blur.clone());
    }
}