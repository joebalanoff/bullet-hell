package engine.core;

import engine.listeners.MousepadListener;
import engine.scenes.SceneManager;
import engine.listeners.Input;
import engine.listeners.KeyboardListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {
    private final JFrame frame;
    private final MainThread mainThread;
    private final SceneManager sceneManager;

    private final KeyboardListener keyboardListener;
    private final MousepadListener mousepadListener;

    public Window(int width, int height, String title) {
        this.frame = new JFrame(title);
        this.frame.setSize(width, height);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.sceneManager = new SceneManager(this);

        this.keyboardListener = new KeyboardListener();
        this.mousepadListener = new MousepadListener(sceneManager);
        this.frame.addKeyListener(keyboardListener);
        this.frame.addMouseMotionListener(mousepadListener);
        new Input(this.keyboardListener, mousepadListener);

        this.mainThread = new MainThread(this);
        new Thread(this.mainThread).start();
        this.frame.add(mainThread);

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainThread.stop();
                System.exit(0);
            }
        });

        this.frame.setFocusable(true);
        this.frame.requestFocusInWindow();
        this.frame.setVisible(true);
    }

    public int getWidth() { return frame.getWidth(); }
    public int getHeight() { return frame.getHeight(); }

    public JFrame getFrame() { return frame; }

    public MainThread getThread() { return mainThread; }

    public SceneManager getSceneManager() { return sceneManager; }

    public KeyboardListener getKeyboardListener() {
        return this.keyboardListener;
    }
}
