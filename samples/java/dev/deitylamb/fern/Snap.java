package dev.deitylamb.fern;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import dev.deitylamb.fern.common.Color;
import dev.deitylamb.fern.common.Easings;
import dev.deitylamb.fern.common.Easings.Ease;
import dev.deitylamb.fern.flows.Flow;

public class Snap extends JPanel {

  private final Ease easing = Easings.springEase(1.3, 350, 30, 0.5);
  private final int PADDING = 50;

  private final Flow<Graphics> flow = Fern
      .<Graphics>flow(2000)
      .ease(easing)
      .circular()
      .speed(2)
      .delay(100)
      .loop();

  public Snap() {

    setSize(768, 256);
    setBackground(new java.awt.Color(0xff151515));
    setOpaque(true);

    flow.play();

  }

  @Override
  protected void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    ((Graphics2D) gui).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    ((Graphics2D) gui).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    drawTrack((Graphics2D) gui);

    flow.tick(gui, 16);

    int size = 50;

    int x = (int) flow.lerp(PADDING, getWidth() - size - PADDING);
    int y = getHeight() / 2 - size / 2;

    Color color = flow.lerp(Color.RED.withBlue(80), Color.BLUE.withRed(80));
    gui.setColor(new java.awt.Color(color.argb(), true));
    gui.fillRoundRect(x, y, size, size, 12, 12);
  }

  private void drawTrack(Graphics2D g2) {
    float[] dashPattern = { 15, 10 };
    g2.setStroke(new BasicStroke(
        1,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        10,
        dashPattern,
        0));
    g2.setColor(java.awt.Color.DARK_GRAY);

    g2.drawLine(PADDING, getHeight() / 2, getWidth() - PADDING, getHeight() / 2);

  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("fern");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(768, 256);
      frame.add(new Snap());
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);

      new Timer(16, e -> frame.repaint()).start();

    });
  }
}
