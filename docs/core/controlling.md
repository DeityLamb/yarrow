# Control flow
Transitions have a few methods to control the animation flow and tween state


| Method                   | Description                                                                       |
|--------------------------|-----------------------------------------------------------------------------------|
| `isRunning()`            | Check if the transition is running                                                |
| `isPaused()`             | Check if the transition is paused                                                 |
|                          |                                                                                   |
| `.play()`                | Play the transition                                                               |
| `.pause()`               | Pause the transition                                                              |
| `.reset()`               | Reset the transition state to the beginning                                       |
|                          |                                                                                   |
| `.restart()`             | Reset the state and play the transition <br/>Alias for `.reset()` and `.play()`   |
| `.stop()`                | Reset the state and pause the transition <br/>Alias for `.reset()` and `.pause()` |
| `.seek(double duration)` | Jump to a specific point in time                                                  |
|                          |                                                                                   |
| `.alpha()`               | Get the current progress (`0.0d` to `1.0d`)                                       |


### Simple example with swing

```java{6-7,17,19}
// frames per second
static final int FPS = 60;
// time between frames
static final int DELTA = 1000 / FPS;
// Create simple ping-pong transition
Transitionable<?> transition = Fern.transition(2000);
transition.play();

JFrame frame = new JFrame("fern");
frame.setSize(512, 128);

frame.add(new JPanel() {
  @Override
  protected void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    transition.tick(DELTA);
    // Get 255 alpha value for the current progress (0.0 to 1.0)
    double transperancy = transition.alpha() * 255d;

    Color color = new Color(80, 0, 255, (int) transperancy);

    gui.setColor(color);
    gui.fillRect(0, 0, getWidth(), getHeight());

  }
});

new Timer(DELTA, a -> frame.repaint()).start();

```
<br/>

<figure>
  <img src="/fade-in.gif" width="100%" style="border-radius: 8px;">
  <figcaption style="color: gray">Fade-in transition</figcaption>  
</figure>
