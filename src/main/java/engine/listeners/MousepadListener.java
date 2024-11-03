package engine.listeners;

import engine.core.Camera;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
import engine.utils.Vector2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MousepadListener implements MouseMotionListener {
    private final SceneManager sceneManager;

    private int mouseX = 0;
    private int mouseY = 0;

    public MousepadListener(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public Vector2 getMousePosition() {
        return new Vector2(mouseX, mouseY);
    }

    public Vector2 getMouseWorldPosition() {
        Scene curScene = sceneManager.getCurrentScene();
        if(curScene == null) return new Vector2();
        Camera camera = curScene.getCamera();
        if(camera == null) return new Vector2();

        double worldX = (mouseX / camera.getZoom()) + camera.getX();
        double worldY = (mouseY / camera.getZoom()) + camera.getY();

        return new Vector2(worldX, worldY);
    }
}
