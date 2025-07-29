package dev.deitylamb.fern;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dev.deitylamb.fern.common.Color;

public class GradientSample extends JPanel {

  private final int PADDING = 64;

  GradientSample() {
    setBackground(new java.awt.Color(0xff151515));
  }

  @Override
  public void paintComponent(Graphics gui) {
    super.paintComponent(gui);

    int height = getHeight() - PADDING * 2;
    int width = getWidth() - PADDING * 2;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    Color top = Color.RED.withBlue(80);
    Color bottom = Color.BLUE.withRed(80);

    for (int y = 0; y < height; y++) {
      float t = (float) y / (height - 1);
      Color interpolated = Color.lerp(t, top, bottom);
      int color = interpolated.argb();
      for (int x = 0; x < width; x++) {
        pixels[y * width + x] = color;
      }
    }
    gui.drawImage(image, PADDING, PADDING, null);
  }

  public static void main(String[] args) throws InterruptedException {

    JFrame frame = new JFrame("fern");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 512);
    frame.add(new GradientSample());
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);

  }
}
