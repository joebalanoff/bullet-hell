package game.scenes.lobby;

import engine.scenes.Entity;
import engine.scenes.Scene;
import game.entities.Player;

import java.awt.*;

public class SceneDoor extends Entity {
    private final double interactionDistance;
    private final Runnable onEnter;

    private final Player player;


    public SceneDoor(Scene scene, double interactionDistance, Runnable onEnter) {
        super(scene);

        this.interactionDistance = interactionDistance;
        this.onEnter = onEnter;

        player = scene.getEntity(Player.class);
    }

    @Override
    public void onUpdate(double delta) {
        if(player == null) return;
        if(player.position.add(position.multiply(-1)).getMagnitude() < interactionDistance) {
            onEnter.run();
        }
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int) position.x, (int) position.y, 50, 50);
    }
}
