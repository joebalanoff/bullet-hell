package engine.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainThread extends JPanel implements Runnable {
    private final Window window;
    private volatile boolean running;

    public MainThread(Window window) {
        this.window = window;
        this.running = true;
        setFocusable(true);
    }

    @Override
    public void run() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        long lastLoopTime = System.nanoTime();

        while(running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta = updateLength / 1_000_000_000.0;

            window.getSceneManager().onUpdate(delta);
            window.getSceneManager().updateScene(delta);
            if(window.getKeyboardListener() != null) window.getKeyboardListener().onUpdate();

            repaint();
            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double pixelationFactor = 0.1;

        // Get current panel dimensions
        int width = getWidth();
        int height = getHeight();

        // Calculate the scaled-down size based on pixelation factor
        int pixelatedWidth = (int) (width * pixelationFactor);
        int pixelatedHeight = (int) (height * pixelationFactor);

        // Make sure pixelated dimensions are at least 1x1 to avoid errors
        pixelatedWidth = Math.max(1, pixelatedWidth);
        pixelatedHeight = Math.max(1, pixelatedHeight);

        // Create a smaller BufferedImage
        BufferedImage pixelatedImage = new BufferedImage(pixelatedWidth, pixelatedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gPixelated = pixelatedImage.createGraphics();

        // Draw your scene at the lower resolution
        gPixelated.scale(pixelationFactor, pixelationFactor);
        window.getSceneManager().drawScene(g2d);
        gPixelated.dispose();

        // Draw the pixelated image scaled back up to the full size
        g2d.drawImage(pixelatedImage, 0, 0, width, height, null);
    }

    public void stop() { running = false; }
}
