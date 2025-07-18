package dev.deitylamb.fern.transitions.decorators;

import dev.deitylamb.fern.Formattable;
import dev.deitylamb.fern.transitions.Transitionable;

public class LoopTransition<T> extends TransitionDecorator<T> {

    public LoopTransition(Transitionable<T> transition) {
        super(transition);
    }

    @Override
    public void tick(T gui, float delta) {

        boolean run = isRunning();

        super.tick(gui, delta);

        if (run && !isRunning()) {
            this.restart();
        }
    }

    @Override
    public LoopTransition<T> chain(Transitionable<T> transition) {
        return new LoopTransition<>(this.inner.chain(transition));
    }

    @Override
    public Transitionable<T> reverse() {
        return new LoopTransition<>(inner.reverse());
    }

    @Override
    public LoopTransition<T> clone() {
        return new LoopTransition<>(inner);
    }

    @Override
    public String toString() {
        return display();
    }

    @Override
    public String display(int depth) {

        String tab = Formattable.indent(depth);

        return "LoopTransition {\n" +
                tab + Formattable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
                tab + "}";
    }

}
