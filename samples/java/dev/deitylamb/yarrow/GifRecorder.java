package dev.deitylamb.yarrow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GifRecorder {

    private final JFrame target;
    private final int totalFrames;
    private final String outputFile;
    private final boolean loopGif;
    private final int tickMillis;
    private Method tickMethod = null;
    private boolean tickMethodChecked = false;

    public static void main(String[] args) throws IOException {
        GifRecorder recorder = new GifRecorder(Snap.createFrame(), 30, 3100, "snap.gif", true);
        recorder.record();
        System.out.println("Recorded");
    }

    public GifRecorder(JFrame target, int fps, int animationDurationMs, String outputFile, boolean loopGif) {
        this.target = target;
        this.tickMillis = 1000 / fps;
        this.totalFrames = (int) Math.round((animationDurationMs / 1000.0) * fps);
        this.outputFile = outputFile;
        this.loopGif = loopGif;
    }

    public void record() throws IOException {
        try (ImageOutputStream output = new FileImageOutputStream(new File(outputFile))) {
            GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_RGB, tickMillis, loopGif);

            // Pre-allocate buffer to avoid repeated allocations
            BufferedImage frame = new BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = frame.createGraphics();

            // Set rendering hints for better performance
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

            for (int i = 0; i < totalFrames; i++) {

                // Ensure painting is done on EDT and wait for completion
                final BufferedImage currentFrame = frame;
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        target.paint(currentFrame.getGraphics());
                    });
                } catch (Exception e) {
                    // Fallback to direct painting if EDT fails
                    target.paint(g2);
                }

                writer.writeToSequence(frame);

                // Advance animation
                advanceAnimation(frame.getGraphics());

                // Small delay to allow proper frame processing
                try {
                    Thread.sleep(Math.max(1, tickMillis / 10));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            g2.dispose();
            writer.close();
        }
    }

    private void advanceAnimation(Graphics graphics) {
        if (!tickMethodChecked) {
            try {
                tickMethod = target.getClass().getMethod("tick", Graphics.class, int.class);
                tickMethod.setAccessible(true);
            } catch (NoSuchMethodException ignored) {
                // Method doesn't exist
            }
            tickMethodChecked = true;
        }

        if (tickMethod != null) {
            try {
                tickMethod.invoke(target, graphics, tickMillis);
            } catch (Exception ignored) {
                // Fallback to repaint
                target.repaint();
            }
        } else {
            target.repaint();
            // Give repaint some time to process
            try {
                SwingUtilities.invokeAndWait(() -> {
                    // Empty runnable to ensure EDT processes pending events
                });
            } catch (Exception ignored) {
                // Continue if EDT synchronization fails
            }
        }
    }

    static class GifSequenceWriter {

        private final ImageWriter gifWriter;
        private final ImageWriteParam imageWriteParam;
        private final IIOMetadata imageMetaData;

        public GifSequenceWriter(ImageOutputStream outputStream, int imageType, int delayMS, boolean loop)
                throws IOException {
            gifWriter = getWriter();
            imageWriteParam = gifWriter.getDefaultWriteParam();

            // Optimize compression for speed over size
            if (imageWriteParam.canWriteCompressed()) {
                imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                String[] compressionTypes = imageWriteParam.getCompressionTypes();
                if (compressionTypes != null && compressionTypes.length > 0) {
                    imageWriteParam.setCompressionType(compressionTypes[0]);
                    imageWriteParam.setCompressionQuality(0.8f); // Good balance of quality/speed
                }
            }

            ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
            imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

            configureMetadata(delayMS, loop);

            gifWriter.setOutput(outputStream);
            gifWriter.prepareWriteSequence(null);
        }

        private void configureMetadata(int delayMS, boolean loop) throws IOException {
            String metaFormatName = imageMetaData.getNativeMetadataFormatName();
            IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

            // Configure frame delay and disposal
            IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
            graphicsControlExtensionNode.setAttribute("disposalMethod", "restoreToBackgroundColor");
            graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
            graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
            graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(Math.max(1, delayMS / 10)));
            graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

            // Configure looping
            if (loop) {
                IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
                IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
                child.setAttribute("applicationID", "NETSCAPE");
                child.setAttribute("authenticationCode", "2.0");
                child.setUserObject(new byte[]{0x1, 0x0, 0x0}); // Infinite loop
                appExtensionsNode.appendChild(child);
            }

            imageMetaData.setFromTree(metaFormatName, root);
        }

        public void writeToSequence(RenderedImage img) throws IOException {
            gifWriter.writeToSequence(new IIOImage(img, null, imageMetaData), imageWriteParam);
        }

        public void close() throws IOException {
            gifWriter.endWriteSequence();
            gifWriter.dispose();
        }

        private static ImageWriter getWriter() throws IOException {
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("gif");
            if (!iter.hasNext()) {
                throw new IOException("No GIF Image Writers Exist");
            }
            return iter.next();
        }

        private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
            for (int i = 0; i < rootNode.getLength(); i++) {
                if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                    return (IIOMetadataNode) rootNode.item(i);
                }
            }
            IIOMetadataNode node = new IIOMetadataNode(nodeName);
            rootNode.appendChild(node);
            return node;
        }
    }

}
