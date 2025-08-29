package dev.deitylamb.fern.tracks;

import java.util.Optional;

import dev.deitylamb.fern.Flow;
import dev.deitylamb.fern.Tickable;

public class Focus<T> implements Tickable<T> {
    private final Flow<T> focus;
    private final Flow<T> blur;
    private boolean isFocused = false;

    public Focus(Flow<T> focus, Flow<T> blur) {
        this.focus = focus;
        this.blur = blur;
    }

    public Optional<Flow<T>> active() {
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
    public Focus<T> clone() {
        return new Focus<>(this.focus.clone(), this.blur.clone());
    }
}