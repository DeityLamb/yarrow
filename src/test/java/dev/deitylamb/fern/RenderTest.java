package dev.deitylamb.fern;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.common.Color;

public class RenderTest {

  @Test
  public void renderGradient() throws InterruptedException {
    int width = 300;
    int height = 300;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    Color top = Color.RED;
    Color bottom = Color.WHITE;

    for (int y = 0; y < height; y++) {
      float t = (float) y / (height - 1);
      Color interpolated = Color.lerp(t, top, bottom);
      int color = interpolated.argb();
      for (int x = 0; x < width; x++) {
        pixels[y * width + x] = color;
      }
    }

    JPanel panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(width, height);
      }
    };

    JFrame frame = new JFrame("fernwindow");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);

    Thread.sleep(100000);
    // frame.dispose();
  }
}
