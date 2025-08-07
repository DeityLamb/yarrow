package dev.deitylamb.fern.flows.decorators;

import java.util.Arrays;
import java.util.function.Function;

import dev.deitylamb.fern.common.Displayable;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.flows.Flow;
import dev.deitylamb.fern.flows.SequenceFlow;

public class EaseFlow<T> extends FlowDecorator<T> {

  private final Ease ease;

  public EaseFlow(Flow<T> inner, Ease ease) {
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
  public FlowDecorator<T> clone() {
    return new EaseFlow<>(inner.clone(), ease);
  }

  @Override
  public Flow<T> speed(double speed) {
    return new EaseFlow<>(inner.speed(speed), ease);
  }

  @Override
  public Flow<T> then(Flow<T> flow) {
    return new SequenceFlow<>(Arrays.asList(this.clone(), flow.clone()));
  }

  @Override
  public String toString() {
    return display();
  }

  @Override
  public String display(int depth) {
    String tab = Displayable.indent(depth);

    return "EaseFlow {\n" +
        tab + Displayable.INDENT + "inner=" + inner.display(depth + 1) + "\n" +
        tab + "}";
  }
}
