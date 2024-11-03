package engine.scenes;

import engine.core.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Scene {
    private SceneManager sceneManager;
    private int buildIndex = -1;
    protected Camera camera;
    protected BufferedImage background;

    private ArrayList<SceneArea> sceneAreas;
    private ArrayList<Entity> entities;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.buildIndex = this.sceneManager.getScenes().size();
        this.camera = new Camera(sceneManager.getWindow());

        this.sceneAreas = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    public SceneArea addArea(SceneArea area) {
        sceneAreas.add(area);
        return area;
    }

    public <T extends Entity> T addEntity(T entity) {
        entities.add(entity);
        return entity;
    }

    public <T extends Entity> T getEntity(Class<T> entityType) {
        for(Entity entity : entities) {
            if(entityType.isInstance(entity)) {
                return entityType.cast(entity);
            }
        }
        return null;
    }

    public abstract void onEnter();
    public void preRender(Graphics2D g2d) {
        g2d.setColor(new Color(33, 33, 44));
        g2d.fillRect(0,0, getWidth(), getHeight());
        if(camera != null) {
            g2d.scale(camera.getZoom(), camera.getZoom());
            g2d.translate(-camera.getX(), -camera.getY());
        }
        if(background != null)
            g2d.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
    }
    public void postRender(Graphics2D g2d) {
        int i = 0;
        for(i = sceneAreas.size() - 1; i >= 0; i--) {
            sceneAreas.get(i).onDebugDraw(g2d);
        }
        for(i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).onDraw(g2d);
        }



        if(camera != null) {
            g2d.translate(camera.getX(), camera.getY());
            g2d.scale(1 / camera.getZoom(), 1 / camera.getZoom());
        }
    }
    public void updateAreas(double delta) {
        if(sceneAreas.isEmpty()) return;
        for(int i = sceneAreas.size() - 1; i >= 0; i--) {
            sceneAreas.get(i).onUpdate(delta);
        }
    }
    public void bubbleArea(SceneArea area) {
        if(!sceneAreas.contains(area)) return;
        sceneAreas.remove(area);
        sceneAreas.add(0, area);
    }
    public void updateEntities(double delta) {
        for(Entity entity : entities) {
            entity.onUpdate(delta);
        }
    }
    public abstract void onUpdate(double delta);
    public abstract void onDraw(Graphics2D g2d);
    public abstract void onExit();

    public SceneArea getActiveArea() {
        for(SceneArea area : sceneAreas) {
            if(area.containsPlayer()) {
                for(SceneArea connectedArea : area.connectedAreas) {
                    if(connectedArea.containsPlayer()) return connectedArea;
                }
                return area;
            }
        }
        return null;
    }

    public int getWidth() { return sceneManager.getWindow().getWidth(); }
    public int getHeight() { return sceneManager.getWindow().getHeight(); }

    public Camera getCamera() { return camera; }
}
