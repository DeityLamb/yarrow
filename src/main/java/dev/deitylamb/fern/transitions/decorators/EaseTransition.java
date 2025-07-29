package dev.deitylamb.fern.transitions.decorators;

import java.util.Arrays;
import java.util.function.Function;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.transitions.SequenceTransition;
import dev.deitylamb.fern.transitions.Transitionable;

public class EaseTransition<T> extends TransitionDecorator<T> {

  private final Ease ease;

  public EaseTransition(Transitionable<T> inner, Ease ease) {
    super(inner);
    this.ease = ease;
  }

  @Override
  public double alpha() {
    return ease.apply(inner.alpha());
  }

  @Override
  public double alpha(Function<Double, Double> ease) {
    return inner.alpha(ease);
  }

  @Override
  public TransitionDecorator<T> clone() {
    return new EaseTransition<>(inner.clone(), ease);
  }

  @Override
  public Transitionable<T> speed(double speed) {
    return new EaseTransition<>(inner.speed(speed), ease);
  }

  @Override
  public Transitionable<T> then(Transitionable<T> transition) {
    return new SequenceTransition<>(Arrays.asList(this.clone(), transition.clone()));
  }

  @Override
  public String toString() {
    return display();
  }

  @Override
  public String display(int depth) {
    String tab = Displayable.indent(depth);

    return "EaseTransition {\n" +
        tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
        tab + "}";
  }
}
