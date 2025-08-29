# Control flow

`Flow` provides several methods to control animation and tween state   

| Methods                  | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `.play()`                | Play the flow                                                               |
| `.pause()`               | Pause the flow                                                              |
| `.reset()`               | Reset the flow state to the beginning                                       |
| `.seek(double duration)` | Jump to a specific point in time                                            |
| `.restart()`             | Reset the state and play the flow <br/>Alias for `.reset()` and `.play()`   |
| `.stop()`                | Reset the state and pause the flow <br/>Alias for `.reset()` and `.pause()` |
| `.alpha()`               | Get the current progress (`0.0d` to `1.0d`)                                 |
| `isRunning()`            | Check if the flow is running                                                |
| `isPaused()`             | Check if the flow is paused                                                 |

You can create a simple flow like this

```java
import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Flow;

// Graphics is any type of object that you want to use in hooks
private final Flow<Graphics> flow = Fern
  .<Graphics>flow(100)
  // this hook will be called every time the tick method is called
  .onTick((graphics, flow) -> {
    double opacity = flow.alpha();
    graphics.setColor(1, 1, 1, opacity);
  });

// Start transition whenever you want
flow.play(); 

public void whateverRender(Graphics gui, double delta) {
  // Tick will update the alpha of the flow
  flow.tick(gui, 10); // alpha() - 0.1
  flow.tick(gui, 50); // alpha() - 0.6

  // we can pause it
  flow.pause();

   // so progress stops at 0.6
  flow.tick(gui, 100); // alpha() - 0.6
  flow.tick(gui, 100); // alpha() - 0.6

  // resume it
  flow.play();

  flow.tick(gui, 10); // alpha() - 0.7

  // or restart it
  flow.restart();

  flow.tick(gui, 10); // alpha() - 0.1
}

```

Or without any Graphics
```java
import dev.deitylamb.fern.Fern;
import dev.deitylamb.fern.Flow;

private final Flow<Void> flow = Fern.flow(100);
flow.play(); 

flow.tick(10);
flow.tick(50);

flow.alpha(); // 0.6

```
## Simple example with Swing

```java
// Frames per second
static final int FPS = 60;
// Time between frames
static final int DELTA = 1000 / FPS;

static final Flow<Void> flow = Fern.flow(2000); // [!code highlight]

JFrame frame = new JFrame("fern");
frame.setSize(512, 128);

flow.play(); // [!code highlight]
frame.add(new JPanel() {
  @Override
  protected void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    flow.tick(DELTA); // [!code highlight]
    // Get 255 alpha value for the current progress (0.0 to 1.0) // [!code highlight]
    int transparency = (int) (flow.alpha() * 255d); // [!code highlight]

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
int transparency = (int) (flow.alpha() * 255d);
```

This looks a little clunky  
Fortunately, Fern provides a handy method to linearly interpolate between two values based on the current alpha  
<br/>
It's an alias for `FernUtils.lerp(alpha, from, to)`

```java
int transparency = (int) (flow.alpha() * 255d); // [!code --]
int transparency = flow.lerp(0, 255); // [!code ++]
```

## Color interpolation

There is also a shortcut method to interpolate between two colors  
`dev.deitylamb.fern.common.Color`

```java
import dev.deitylamb.fern.common.Color; // [!code ++]

Color color = flow.lerp(Color.RED, Color.BLUE);

color.red();
color.green();
color.blue();
```
