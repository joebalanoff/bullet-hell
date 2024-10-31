package engine.scenes;

import engine.core.Window;

import java.awt.*;
import java.util.ArrayList;

public class SceneManager {
    private final Window window;
    private ArrayList<Scene> scenes;
    private int currentSceneIndex;

    public SceneManager(Window window) {
        this.window = window;
        this.scenes = new ArrayList<>();
        this.currentSceneIndex = -1;
    }

    public void addScene(Scene scene) {
        scene.setSceneManager(this);
        scenes.add(scene);
    }

    public void setScene(int sceneIndex) {
        if(sceneIndex < 0 || sceneIndex > scenes.size()) return;
        if(currentSceneIndex == sceneIndex) return;

        Scene oldScene = getCurrentScene();
        if(oldScene != null) oldScene.onExit();

        currentSceneIndex = sceneIndex;
        Scene newScene = getCurrentScene();
        if(newScene != null) newScene.onEnter();
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public Scene getCurrentScene() {
        if(currentSceneIndex < 0 || currentSceneIndex > scenes.size()) return null;
        return scenes.get(currentSceneIndex);
    }

    public void updateScene(double delta) {
        Scene curScene = getCurrentScene();
        if(curScene != null) {
            curScene.onUpdate(delta);
            curScene.updateEntities(delta);
            curScene.camera.onUpdate(delta);
        }
    }

    public void drawScene(Graphics2D g2d) {
        Scene curScene = getCurrentScene();
        if(curScene != null) {
            curScene.preRender(g2d);
            curScene.onDraw(g2d);
            curScene.postRender(g2d);
        }
    }

    public Window getWindow() { return window; }
}
