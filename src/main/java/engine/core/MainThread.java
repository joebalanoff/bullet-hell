package engine.core;

import javax.swing.*;
import java.awt.*;

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

            double delta = updateLength / ((double) OPTIMAL_TIME);

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
        window.getSceneManager().drawScene(g2d);
    }

    public void stop() { running = false; }
}
