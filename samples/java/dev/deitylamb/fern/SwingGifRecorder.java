package dev.deitylamb.fern;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
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

public class SwingGifRecorder {
  private final JFrame target;
  private final int totalFrames;
  private final String outputFile;
  private final boolean loopGif;
  private final int tickMillis;

  public SwingGifRecorder(JFrame target, int fps, int animationDurationMs, String outputFile, boolean loopGif) {
    this.target = target;
    this.tickMillis = 1000 / fps;
    this.totalFrames = (int) Math.round((animationDurationMs / 1000.0) * fps);
    this.outputFile = outputFile;
    this.loopGif = loopGif;
  }

  public void record() throws IOException {
    try (ImageOutputStream output = new FileImageOutputStream(new File(outputFile))) {
      GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, tickMillis, loopGif);

      for (int i = 0; i < totalFrames; i++) {
        BufferedImage frame = new BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = frame.createGraphics();
        target.paint(g2);
        g2.dispose();

        writer.writeToSequence(frame);

        // manually advance animation if it has a "tick" method
        try {
          target.getClass().getMethod("tick", Graphics.class, int.class)
              .invoke(target, frame.getGraphics(), tickMillis);
        } catch (Exception ignored) {
          // if no tick() method, rely on repaint logic
          target.repaint();
        }
      }
      writer.close();
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
      ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
      imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

      String metaFormatName = imageMetaData.getNativeMetadataFormatName();
      IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

      IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
      graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
      graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
      graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
      graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delayMS / 10));
      graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

      IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
      IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
      child.setAttribute("applicationID", "NETSCAPE");
      child.setAttribute("authenticationCode", "2.0");

      int loopCount = loop ? 0 : 1;
      child.setUserObject(new byte[] { 0x1, (byte) (loopCount & 0xFF), (byte) ((loopCount >> 8) & 0xFF) });
      appExtensionsNode.appendChild(child);

      imageMetaData.setFromTree(metaFormatName, root);
      gifWriter.setOutput(outputStream);
      gifWriter.prepareWriteSequence(null);
    }

    public void writeToSequence(RenderedImage img) throws IOException {
      gifWriter.writeToSequence(new IIOImage(img, null, imageMetaData), imageWriteParam);
    }

    public void close() throws IOException {
      gifWriter.endWriteSequence();
    }

    private static ImageWriter getWriter() throws IOException {
      Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("gif");
      if (!iter.hasNext())
        throw new IOException("No GIF Image Writers Exist");
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

  public static void main(String[] args) throws IOException {

    SwingGifRecorder recorder = new SwingGifRecorder(FadeInOutSample.createFrame(), 30, 6000, "fade-in.gif", true);
    recorder.record();

    System.out.println("Recorded");

  }

}
