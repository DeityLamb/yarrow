package dev.deitylamb.yarrow;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dev.deitylamb.yarrow.common.Easings;

public class FadeInOut {

  // frames per second
  private static int FPS = 60;
  // time between frames
  private static int delta = 1000 / FPS;

  private static final Flow<?> flow = Yarrow.flow(2000)
      .delay(100)
      .ease(Easings::easeOutCubic)
      .circular()
      .loop();

  public static void main(String[] args) {

    Frame frame = createFrame();

    new Timer(delta, a -> frame.repaint()).start();
  }

  public static JFrame createFrame() {

    flow.play();

    JFrame frame = new JFrame("yarrow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 128);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);

    frame.add(new JPanel() {
      @Override
      protected void paintComponent(Graphics gui) {
        super.paintComponent(gui);

        flow.tick(delta);

        Color color = new Color(80, 0, 255,
            (int) (flow.alpha() * 255D));

        gui.setColor(color);
        gui.fillRect(0, 0, getWidth(), getHeight());

      }
    });

    return frame;
  }
}
