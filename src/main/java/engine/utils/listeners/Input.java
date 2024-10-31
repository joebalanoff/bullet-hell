package engine.utils.listeners;

public class Input {
    public static Input instance;
    private final KeyboardListener keyboardListener;

    public Input(KeyboardListener keyboardListener) {
        instance = this;
        this.keyboardListener = keyboardListener;
    }

    public static boolean isKeyPressed(int keyCode) {
        return instance.keyboardListener.keyPressed(keyCode);
    }

    public static boolean isKeyDown(int keyCode) {
        return instance.keyboardListener.keyDown(keyCode);
    }
}
