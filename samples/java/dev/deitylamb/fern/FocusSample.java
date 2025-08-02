package dev.deitylamb.fern;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dev.deitylamb.fern.common.Easings;
import dev.deitylamb.fern.flows.FocusFlow;
import dev.deitylamb.fern.transitions.Transitionable;

public class FocusSample {

  // frames per second
  private static int FPS = 30;
  // time between frames
  private static int delta = 1000 / FPS;

  public static void main(String[] args) {

    Frame frame = createFrame();

    new Timer(delta, a -> frame.repaint()).start();
  }

  public static JFrame createFrame() {

    Transitionable<?> transition = Fern.transition(500)
        .ease(Easings::easeOutQuad);

    FocusFlow<?> focus = Fern.focus(transition);

    JFrame frame = new JFrame("fern");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 512);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);

    Mouse mouse = new Mouse();
    frame.addMouseMotionListener(mouse);

    frame.add(new JPanel() {

      {
        setBackground(new Color(16, 16, 20));
      }

      @Override
      protected void paintComponent(Graphics gui) {
        super.paintComponent(gui);

        ((Graphics2D) gui).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) gui).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int size = 128;
        int x = getWidth() / 2 - size / 2;
        int y = getHeight() / 2 - size / 2;

        focus.tick(delta);
        focus.setFocus(mouse.isHoveringSquare(x, y, size));

        int[] radius = new int[] { 64, 64 };

        focus.active().ifPresent(v -> {
          gui.setColor(new Color(80, 0, 255, v.lerp(100, 255)));
          radius[0] = v.lerp(64, 16);
          radius[1] = v.lerp(64, 16);
        });

        gui.fillRoundRect(x, y, size, size, radius[0], radius[1]);

        gui.setColor(Color.WHITE);

        gui.fillRect(
            mouse.getX() - 8,
            mouse.getY() - 8,
            8, 8);

      }
    });

    return frame;
  }

  private static class Mouse extends MouseMotionAdapter {

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
      return this.x >= x && this.x <= x + size && this.y >= y && this.y <= y + size;
    }
  }

}
