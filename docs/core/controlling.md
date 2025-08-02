# Control flow

Transitions provide several methods to control animation flow and tween state

| Methods                  | Description                                                                       |
| ------------------------ | --------------------------------------------------------------------------------- |
| `.play()`                | Play the transition                                                               |
| `.pause()`               | Pause the transition                                                              |
| `.reset()`               | Reset the transition state to the beginning                                       |
| `.seek(double duration)` | Jump to a specific point in time                                                  |
| `.restart()`             | Reset the state and play the transition <br/>Alias for `.reset()` and `.play()`   |
| `.stop()`                | Reset the state and pause the transition <br/>Alias for `.reset()` and `.pause()` |
| `.alpha()`               | Get the current progress (`0.0d` to `1.0d`)                                       |
| `isRunning()`            | Check if the transition is running                                                |
| `isPaused()`             | Check if the transition is paused                                                 |

## Simple example with Swing

```java
// Frames per second
static final int FPS = 60;
// Time between frames
static final int DELTA = 1000 / FPS;
// Create simple ping-pong transition
Transitionable<?> transition = Fern.transition(2000); // [!code highlight]
transition.play(); // [!code highlight]

JFrame frame = new JFrame("fern");
frame.setSize(512, 128);

frame.add(new JPanel() {
  @Override
  protected void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    transition.tick(DELTA); // [!code highlight]
    // Get 255 alpha value for the current progress (0.0 to 1.0) // [!code highlight]
    int transparency = (int) (transition.alpha() * 255d); // [!code highlight]

    Color color = new Color(80, 0, 255, transparency);

    gui.setColor(color);
    gui.fillRect(0, 0, getWidth(), getHeight());
  }
});

new Timer(DELTA, a -> frame.repaint()).start();
```

## Code result

<figure>
  <img src="/assets/examples/fade-in.gif" width="100%" style="border-radius: 8px;">
  <figcaption style="color: gray">Fade-in transition</figcaption>  
</figure>

## Linear interpolation

In this section, we generate a number from 0 to 255 based on the alpha progress \[0, 1]

```java
int transparency = (int) (transition.alpha() * 255d);
```

This looks a little clunky  
Fortunately, Fern provides a handy method to linearly interpolate between two values based on the current alpha  
<br/>
It's an alias for `FernUtils.lerp(alpha, from, to)`

```java
int transparency = (int) (transition.alpha() * 255d); // [!code --]
int transparency = transition.lerp(0, 255); // [!code ++]
```

## Color interpolation

There is also a shortcut method to interpolate between two colors  
`dev.deitylamb.fern.common.Color`

```java
import dev.deitylamb.fern.common.Color; // [!code ++]

Color color = transition.lerp(Color.RED, Color.BLUE);

color.red();
color.green();
color.blue();
```
