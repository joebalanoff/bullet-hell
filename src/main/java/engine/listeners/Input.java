package engine.listeners;

import engine.utils.Vector2;

public class Input {
    public static Input instance;
    private final KeyboardListener keyboardListener;
    private final MousepadListener mousepadListener;

    public Input(KeyboardListener keyboardListener, MousepadListener mousepadListener) {
        instance = this;
        this.keyboardListener = keyboardListener;
        this.mousepadListener = mousepadListener;
    }

    public static boolean isKeyPressed(int keyCode) {
        return instance.keyboardListener.keyPressed(keyCode);
    }

    public static boolean isKeyDown(int keyCode) {
        return instance.keyboardListener.keyDown(keyCode);
    }

    public static boolean isKeyReleased(int keyCode) {
        return instance.keyboardListener.keyReleased(keyCode);
    }

    public static int getMouseX() { return instance.mousepadListener.getMouseX(); }
    public static int getMouseY() { return instance.mousepadListener.getMouseY(); }
    public static Vector2 getMousePosition() { return instance.mousepadListener.getMousePosition(); }
    public static Vector2 getMouseWorldPosition() { return instance.mousepadListener.getMouseWorldPosition(); }
}
