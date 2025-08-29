# Tracks

Yarrow also provides a special "tracks", it's Flow wrappes for specific use cases.
It allows you to create a focus-based animation, where the transition is only active when it is focused on.

## Usage
```java

FocusTrack focus = Yarrow.focus(
    Yarrow.transition(200).ease(Easings::easeOutCubic)
);

protected void paintComponent(Graphics gui) {
  focus.tick(gui, delta);
  focus.setFocus(/* mouse is hovering, notification popped up, etc */);
  focus.active(); // Optional<Flow<Void>>

}
```
::: tip
Blur transition will be reversed focus transition  
But you can also provide it separately as second parameter
```java
FocusTrack focus = Yarrow.focus(
    Yarrow.transition(200).ease(Easings::easeOutCubic),
    Yarrow.transition(100).reverse() // faster transition without easing
);
:::


## Lets build something

We can create a simple square in the center of the screen that changes its color and corner roundness when hovered over.

::: code-group
```java [Square.java]
class Square extends JPanel {

  private final int size = 128;
  private final double delta;
  
  private Mouse mouse = new Mouse();

  private FocusFlow<?> focus = Yarrow.focus( // [!code highlight]
    Yarrow.transition(500) // [!code highlight]
        .ease(Easings::easeOutQuad)); // [!code highlight]

  public JPanel(double delta) {
    this.delta = delta;
    setBackground(new Color(16, 16, 20));

    addMouseMotionListener(mouse);
  }

  @Override
  protected void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    // get center of the screen
    int x = getWidth() / 2 - size / 2;
    int y = getHeight() / 2 - size / 2;

    focus.tick(delta); // [!code highlight]
    focus.setFocus(mouse.isHoveringSquare(x, y, size)); // [!code highlight]

    int[] radius = new int[] { 64, 64 };

    focus.active().ifPresent(transition -> { // [!code highlight]
      Color color = new Color(80, 0, 255, transition.lerp(100, 255)); // [!code highlight]
      gui.setColor(color); // [!code highlight]
      radius[0] = transition.lerp(64, 16); // [!code highlight]
      radius[1] = transition.lerp(64, 16); // [!code highlight]
    });

    gui.fillRoundRect(x, y, size, size, radius[0], radius[1]);
  }
}
```

```java [FocusSample.java]
public class FocusSample {

  // frames per second
  private static int FPS = 30;
  // time between frames
  private static int delta = 1000 / FPS;

  public static void main(String[] args) {

    JFrame frame = new JFrame("yarrow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 512);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.add(new Sqaure(delta));

    new Timer(delta, a -> frame.repaint()).start();
  }
}
```

```java [Mouse.java]
// Just a simple mouse listener
class Mouse extends MouseMotionAdapter {

  private int x;
  private int y;

  @Override
  public void mouseMoved(MouseEvent e) {
    this.x = e.getX();
    this.y = e.getY();
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public boolean isHoveringSquare(int x, int y, int size) {
    return this.x >= x &&
           this.y >= y  &&
           this.x <= x + size &&
           this.y <= y + size;
  }
}
```
:::

## Code result

<figure>
<center style="background: rgba(16, 16, 20, 1); border-radius: 12px;">
  <img src="/assets/examples/focus.gif" width="40%">
</center>
  <figcaption style="color: gray">Focus transition</figcaption>  
</figure>