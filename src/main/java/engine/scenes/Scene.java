package engine.scenes;

import engine.core.Camera;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene {
    private SceneManager sceneManager;
    private int buildIndex = -1;
    protected Camera camera;

    protected ArrayList<Entity> entities;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.buildIndex = this.sceneManager.getScenes().size();
        this.camera = new Camera(sceneManager.getWindow());
        this.entities = new ArrayList<>();
    }

    public <T extends Entity> T addEntity(T entity) {
        entities.add(entity);
        return entity;
    }

    public abstract void onEnter();
    public void preRender(Graphics2D g2d) {
        if(camera != null) {
            g2d.scale(camera.getZoom(), camera.getZoom());
            g2d.translate(-camera.getX(), -camera.getY());
        }
    }
    public void postRender(Graphics2D g2d) {
        for(Entity entity : entities) {
            entity.onDraw(g2d);
        }

        if(camera != null) {
            g2d.translate(camera.getX(), camera.getY());
            g2d.scale(1 / camera.getZoom(), 1 / camera.getZoom());
        }
    }
    public void updateEntities(double delta) {
        for(Entity entity : entities) {
            entity.onUpdate(delta);
        }
    }
    public abstract void onUpdate(double delta);
    public abstract void onDraw(Graphics2D g2d);
    public abstract void onExit();
}
