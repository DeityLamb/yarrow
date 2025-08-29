# Decorators and Sequences

Transitions can be combined using decorators and sequences  
Each method returns a new transition, so you can easily chain multiple together

| Method                             | Description                                                                                               |
|------------------------------------|-----------------------------------------------------------------------------------------------------------|
| `.delay(double duration)`          | Delays the transition by `duration` milliseconds                                                          |
| `.ease(Ease ease)`                 | Applies an easing function to the transition                                                              |
| `.speed(double speed)`             | Changes the transition duration <br/>**2.0 makes the transition twice as fast**                           |
| `.repeat(int n)`                   | Repeats the transition `n` times                                                                          |
| `.then(Transitionable transition)` | Chains another transition to the end of the current one                                                   |
| `.reverse()`                       | Reverses the direction of the transition <br/>Alias for `.ease(alpha -> 1d - alpha)`                      |
| `.loop()`                          | Repeats the transition forever <br/>Alias for `.repeat(-1)`                                               |
| `.circular()`                      | Plays the transition twice, forwards and backwards <br/>Alias for `transition.then(transition.reverse())` |

## Upgrade previous example

Let's slightly modify the example from the previous section

```java
import dev.deitylamb.yarrow.common.Easings;

Transitionable<?> transition = Yarrow.transition(2000)
  .delay(100) // [!code ++]
  .ease(Easings::easeOutCubic) // [!code ++]
  .circular() // [!code ++]
  .loop(); // [!code ++]
```

### Code result

<figure>
  <img src="/assets/examples/fade-in-out.gif" width="100%" style="border-radius: 8px;">
  <figcaption style="color: gray">Fade-in-out transition with easing and delay</figcaption>  
</figure>

## Examples
You can find more complex examples in this section