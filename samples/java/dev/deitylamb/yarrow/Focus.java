package dev.deitylamb.yarrow;

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

import dev.deitylamb.yarrow.common.Easings;

public class Focus {

    // frames per second
    private final static int FPS = 60;
    // time between frames
    private static final int DELTA = 1000 / FPS;

    private static final Flow<Graphics> flow = Yarrow.<Graphics>flow(150)
            .ease(Easings::easeOutCubic)
            .onTick((g, f) -> {
                g.setColor(new Color(80, 0, 255, f.lerp(100, 255)));
            });

    public static void main(String[] args) {

        Frame frame = createFrame();

        new Timer(DELTA, a -> frame.repaint()).start();
    }

    public static JFrame createFrame() {

        dev.deitylamb.yarrow.tracks.Focus<Graphics> focus = Yarrow.<Graphics>focus(flow);

        JFrame frame = new JFrame("yarrow");
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

                focus.tick(gui, DELTA);
                focus.setFocus(mouse.isHoveringSquare(x, y, size));

                int radius = focus.active().map(flow -> flow.lerp(64, 16)).orElse(64);
                gui.fillRoundRect(x, y, size, size, radius, radius);

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
