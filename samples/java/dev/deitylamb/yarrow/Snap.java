package dev.deitylamb.yarrow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import dev.deitylamb.yarrow.common.Easings;
import dev.deitylamb.yarrow.common.Easings.Ease;

public class Snap {

    // frames per second
    private static final int FPS = 60;
    // time between frames
    private static final int DELTA = 1000 / FPS;

    private static final int PADDING = 50;
    private static final Ease easing = Easings.bezierCubic(1, -0.25, .25, 1.25);

    private static final Flow<Graphics> flow = Yarrow.<Graphics>flow(2000)
            .ease(easing)
            .delay(1000)
            .circular()
            .speed(2)
            .loop();

    public static void main(String[] args) {
        Frame frame = createFrame();
        new Timer(DELTA, a -> frame.repaint()).start();
    }

    public static JFrame createFrame() {
        JFrame frame = new JFrame("yarrow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 256);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.add(new JPanel() {
            {
                setBackground(new Color(0x15, 0x15, 0x15));
            }

            @Override
            protected void paintComponent(Graphics gui) {
                super.paintComponent(gui);

                Graphics2D g2 = (Graphics2D) gui;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

                drawTrack(g2);

                flow.tick(gui, DELTA);

                int size = 50;
                int x = (int) flow.lerp(PADDING, getWidth() - size - PADDING);
                int y = getHeight() / 2 - size / 2;

                dev.deitylamb.yarrow.common.Color color
                        = flow.lerp(dev.deitylamb.yarrow.common.Color.RED.withBlue(80),
                                dev.deitylamb.yarrow.common.Color.BLUE.withRed(80));

                gui.setColor(new Color(color.argb(), true));
                gui.fillRoundRect(x, y, size, size, 12, 12);
            }

            private void drawTrack(Graphics2D g2) {
                float[] dashPattern = {15, 10};
                g2.setStroke(new BasicStroke(
                        1,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10,
                        dashPattern,
                        0));
                g2.setColor(Color.DARK_GRAY);
                g2.drawLine(PADDING, getHeight() / 2, getWidth() - PADDING, getHeight() / 2);
            }
        });

        flow.play();

        return frame;
    }
}
