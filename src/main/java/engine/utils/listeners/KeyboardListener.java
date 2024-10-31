package engine.utils.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyboardListener implements KeyListener {
    private boolean[] keyStates = new boolean[256];
    private boolean[] justPressed = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode < 256) {
            if(!keyStates[keyCode]) {
                justPressed[keyCode] = true;
            }
            keyStates[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStates[e.getKeyCode()] = false;
    }

    public boolean keyPressed(int keyCode) {
        return keyCode < 256 && justPressed[keyCode];
    }

    public boolean keyDown(int keyCode) {
        return keyCode < 256 && keyStates[keyCode];
    }

    public void onUpdate() {
        Arrays.fill(justPressed, false);
    }
}