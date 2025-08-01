package dev.deitylamb.fern;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dev.deitylamb.fern.common.Easings;
import dev.deitylamb.fern.transitions.Transitionable;

public class FadeInOutSample {

  // frames per second
  private static int FPS = 60;
  // time between frames
  private static int delta = 1000 / FPS;

  public static void main(String[] args) {

    Frame frame = createFrame();

    new Timer(delta, a -> frame.repaint()).start();
  }

  public static JFrame createFrame() {

    Transitionable<?> transition = Fern.transition(2000)
        .delay(100)
        .ease(Easings::easeOutCubic)
        .circular()
        .loop();

    transition.play();

    JFrame frame = new JFrame("fern");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 128);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);

    frame.add(new JPanel() {
      @Override
      protected void paintComponent(Graphics gui) {
        super.paintComponent(gui);

        transition.tick(delta);

        Color color = new Color(80, 0, 255,
            (int) (transition.alpha() * 255D));

        gui.setColor(color);
        gui.fillRect(0, 0, getWidth(), getHeight());

      }
    });

    return frame;
  }
}
