package game.entities;

import engine.scenes.Entity;
import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import engine.listeners.Input;
import game.entities.projectiles.Projectile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Entity {
    // Stats
    private int health = 30;

    // Movement
    private double moveSpeed = 200;
    private double sprintSpeed = 400;
    private boolean sprinting = false;
    private final Vector2 input;
    private final Vector2 mouseDirection;

    // Attacking
    private final double attackCooldown = 0.05f;
    private final double maximumAttackCharge = 3f;
    private double attackChargeTimer = 0f;
    private boolean isChargingAttack = false;
    private final ArrayList<Projectile> projectiles;

    // Animation
    private double angle = 0;

    public Player(Scene scene) {
        super(scene);
        this.position.x = (double) getWidth() / 2;
        this.position.y = (double) getHeight() / 2;

        this.input = new Vector2();
        this.mouseDirection = new Vector2();

        this.projectiles = new ArrayList<>();
    }

    @Override
    public void onUpdate(double delta) {
        Vector2 mousePosition = Input.getMouseWorldPosition();
        mouseDirection.x = mousePosition.x - this.position.x;
        mouseDirection.y = (mousePosition.y - 40) - this.position.y;
        mouseDirection.Normalize();

        manageMovement(delta);
        manageAttack(delta);

        for(int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.onUpdate(delta);
        }
    }

    private void manageMovement(double delta) {
        sprinting = Input.isKeyDown(KeyEvent.VK_SHIFT);

        input.x = 0;
        input.y = 0;
        if(Input.isKeyDown(KeyEvent.VK_W)) input.y -= 1;
        if(Input.isKeyDown(KeyEvent.VK_S)) input.y += 1;
        if(Input.isKeyDown(KeyEvent.VK_D)) input.x += 1;
        if(Input.isKeyDown(KeyEvent.VK_A)) input.x -= 1;
        Vector2 nextPosition = position.add(input.normalized().multiply(delta * (sprinting ? sprintSpeed : moveSpeed)));

        SceneArea activeArea = scene.getActiveArea();
        if(activeArea != null) {
            boolean canEnterConnectedArea = false;

            for (SceneArea connectedArea : activeArea.connectedAreas) {
                if (connectedArea.containsPlayer()) {
                    canEnterConnectedArea = !activeArea.locked && !connectedArea.locked;
                    break;
                }
            }

            if(!canEnterConnectedArea) {
                nextPosition.x = Math.max(activeArea.minPosition.x, Math.min(nextPosition.x, activeArea.maxPosition.x));
                nextPosition.y = Math.max(activeArea.minPosition.y, Math.min(nextPosition.y, activeArea.maxPosition.y));
            }
        }

        position.set(nextPosition);

        double targetAngle = 0;
        if(input.x > 0) targetAngle = 20;
        else if(input.x < 0) targetAngle = -20;
        else targetAngle = 0;

        if(sprinting) targetAngle *= 2;

        angle += (targetAngle - angle) * 0.3;
    }

    private void manageAttack(double delta) {
        if(isChargingAttack) {
            if(attackChargeTimer <= maximumAttackCharge) {
                attackChargeTimer += delta * 2;
            } else {
                attackChargeTimer = maximumAttackCharge;
            }
        }
        if(Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            isChargingAttack = true;
            attackChargeTimer = 0f;
        }
        if(Input.isKeyReleased(KeyEvent.VK_SPACE)) {
            isChargingAttack = false;
            if(attackChargeTimer > 0.5f) {

                projectiles.add(new Projectile((int) position.x, (int) position.y, ((int) attackChargeTimer * 10), (int) (attackChargeTimer * 10), 0, mouseDirection.copy(), 500));
            }
            attackChargeTimer = 0f;
        }
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.rotate(Math.toRadians(angle), position.x, position.y);
        double size = 30;
        g2d.fillRoundRect((int) (position.x - size / 2), (int) (position.y - size / 2), (int) size, (int) size, 10, 10);
        g2d.rotate(-Math.toRadians(angle), position.x, position.y);

        if(isChargingAttack) {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect((int) (position.x + mouseDirection.x), (int) (position.y + mouseDirection.y), (int) (attackChargeTimer * 10), (int) (attackChargeTimer * 10));
        }

        g2d.setColor(Color.RED);
        for(int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            g2d.fillRect(projectile.x, projectile.y, projectile.w, projectile.h);
        }
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        scene.getCamera().shake(7.0f, 0.2);
    }
}
