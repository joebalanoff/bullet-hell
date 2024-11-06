package engine.scenes;

import engine.core.Window;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SceneManager {
    private final Window window;
    private ArrayList<Scene> scenes;
    private int currentSceneIndex;

    // Scene Transitions
    private boolean transitioningScenes;
    private double fadeValue = 0;
    private int transitionToIndex;
    private double sceneTransitionTimer;
    private double sceneTransitionTime = 0.6;

    public SceneManager(Window window) {
        this.window = window;
        this.scenes = new ArrayList<>();
        this.currentSceneIndex = -1;

        this.transitioningScenes = false;
        this.fadeValue = 0;
        this.transitionToIndex = -1;
    }

    public void addScene(Scene scene) {
        scene.setSceneManager(this);
        scenes.add(scene);
    }

    public void onUpdate(double delta) {
        if(transitioningScenes) {
            sceneTransitionTimer += delta;
            fadeValue = Math.min(1, sceneTransitionTimer / sceneTransitionTime);
            if(sceneTransitionTimer > sceneTransitionTime) {
                setScene(transitionToIndex);
                transitioningScenes = false;
                sceneTransitionTimer = 0;
            }
        } else {
            if(fadeValue > 0) {
                fadeValue = Math.max(0, fadeValue - delta);
            }
        }
    }

    public void transitionToScene(int sceneIndex) {
        if(transitioningScenes) return;

        if(sceneIndex < 0 || sceneIndex > scenes.size()) return;
        if(currentSceneIndex == sceneIndex) return;

        transitioningScenes = true;
        transitionToIndex = sceneIndex;
        sceneTransitionTimer = 0;
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

            curScene.updateAreas(delta);
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

    public double getFadeValue() { return fadeValue; }
    public Window getWindow() { return window; }
}
