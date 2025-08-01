

# Decorators and Sequences

Transitions can be combined using decorators and sequences.  
Each method returns a new transition, so you can easily chain multiple together.

| Method                             | Description                                                                                                |
|------------------------------------|---------------------------------------------------------------------|
| `.delay(double duration)`          | Delays the transition by `duration` milliseconds|
| `.ease(Ease ease)`                 | Applies an easing function to the transition|
| `.speed(double speed)`             | Change transition duration <br/>__2.0 makes the transition x2 times faster__|
| `.repeat(int n)`                   | Repeats the transition `n` times|
| `.then(Transitionable transition)` | Chains another transition to the end of the current one|
| `.reverse()`                       | Reverses the direction of the transition<br/> Alias for `.ease(alpha -> 1d - alpha)`|
| `.circular()`                      | Plays the transition twice, forwards and backwards<br/> Alias for `transition.then(transition.reverse())` |
| `.loop()`                          | Repeats the transition forever <br/>Alias for `.repeat(-1)`|

Lets slightly modify the example from previous section
```java{4-7}
import dev.deitylamb.fern.common.Easings;

Transitionable<?> transition = Fern.transition(2000)
  .delay(100)
  .ease(Easings::easeOutCubic)
  .circular()
  .loop();
```
<figure>
  <img src="/fade-in-out.gif" width="100%" style="border-radius: 8px;">
  <figcaption style="color: gray">Fade-in-out transition with easing and delay</figcaption>  
</figure>