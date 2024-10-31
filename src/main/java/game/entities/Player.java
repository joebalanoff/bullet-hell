package game.entities;

import engine.scenes.Entity;
import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import engine.utils.listeners.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    private double moveSpeed = 5;
    private double angle = 0;
    private double targetAngle = 0;

    public Player(Scene scene) {
        super(scene);
        this.position.x = getWidth() / 2;
        this.position.y = getHeight() / 2;
    }

    @Override
    public void onUpdate(double delta) {
        Vector2 input = new Vector2();
        if(Input.isKeyDown(KeyEvent.VK_W)) input.y -= 1;
        if(Input.isKeyDown(KeyEvent.VK_S)) input.y += 1;
        if(Input.isKeyDown(KeyEvent.VK_D)) input.x += 1;
        if(Input.isKeyDown(KeyEvent.VK_A)) input.x -= 1;
        Vector2 nextPosition = position.add(input.normalized().multiply(delta * moveSpeed));

        SceneArea activeArea = scene.getActiveArea();
        if(activeArea != null) {
            boolean canEnterConnectedArea = false;
            for(SceneArea connectedArea : activeArea.connectedAreas) {
                if(connectedArea.containsPlayer()) {
                    canEnterConnectedArea = true;
                    break;
                }
            }

            if(!canEnterConnectedArea) {
                nextPosition.x = Math.max(activeArea.minPosition.x, Math.min(nextPosition.x, activeArea.maxPosition.x));
                nextPosition.y = Math.max(activeArea.minPosition.y, Math.min(nextPosition.y, activeArea.maxPosition.y));
            }
        }

        position.set(nextPosition);

        if(input.x > 0) targetAngle = 20;
        else if(input.x < 0) targetAngle = -20;
        else targetAngle = 0;

        angle += (targetAngle - angle) * 0.3;
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.rotate(Math.toRadians(angle), position.x, position.y);
        g2d.fillRoundRect((int) (position.x - 15), (int) (position.y - 15), 30, 30, 10, 10);
        g2d.rotate(-Math.toRadians(angle), position.x, position.y);
    }
}
