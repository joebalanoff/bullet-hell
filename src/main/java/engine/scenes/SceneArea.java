package engine.scenes;

import engine.utils.Vector2;
import game.entities.Player;

import java.awt.*;
import java.util.ArrayList;

public class SceneArea {
    private final Scene scene;

    public boolean followsPlayer = true;
    public boolean containCamera = true;
    public Vector2 minPosition;
    public Vector2 maxPosition;
    public Vector2 cameraOffset;
    public float cameraZoom = 0.5f;
    public Runnable onEnter;
    public Runnable onClear;
    public boolean locked = false;
    private boolean active;

    public final ArrayList<SceneArea> connectedAreas = new ArrayList<>();
    public SceneArea(Scene scene) {
        this.scene = scene;
        this.active = false;
        this.onEnter = () -> {};
        this.onClear = () -> {};
    }

    public void onUpdate(double delta) {
        if(!active && containsPlayer()) {
            activateArea();
        } else if (active && !containsPlayer()) {
            active = false;
        }
    }

    private void activateArea() {
        if(!active) {
            scene.bubbleArea(this);
            onEnter.run();
            active = true;
            if(!scene.getActiveArea().locked) {
                scene.camera.setZoom(cameraZoom);
                if (containCamera) {
                    scene.camera.setMinPosition(minPosition);
                    scene.camera.setMaxPosition(maxPosition);
                }
                if (cameraOffset != null) scene.camera.setOffset(cameraOffset);
                if (followsPlayer) {
                    Player p = scene.getEntity(Player.class);
                    if (p != null) {
                        scene.camera.setFollow(p.position);
                    }
                }
            }
        }
    }

    public void onDebugDraw(Graphics2D g2d) {
        g2d.setColor(containsPlayer() ? Color.GREEN : Color.RED);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect((int) minPosition.x, (int) minPosition.y, (int) (maxPosition.x - minPosition.x), (int) (maxPosition.y - minPosition.y));
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int) minPosition.x, (int) minPosition.y, (int) (maxPosition.x - minPosition.x), (int) (maxPosition.y - minPosition.y));
    }

    public boolean containsPlayer() {
        Player p = scene.getEntity(Player.class);
        if(p == null) return false;
        Vector2 position = p.position;
        return position.x >= minPosition.x && position.y >= minPosition.y &&
                position.x <= maxPosition.x && position.y <= maxPosition.y;
    }

    private boolean connectedToPlayerArea() {
        for(SceneArea area : connectedAreas) {
            if(area.containsPlayer()) return true;
        }
        return false;
    }

    public void connectTo(SceneArea other) {
        if(!connectedAreas.contains(other)) connectedAreas.add(other);
        if(!other.connectedAreas.contains(this)) other.connectedAreas.add(this);
    }
}
