package game.entities;

import engine.scenes.Entity;
import engine.scenes.Scene;
import engine.utils.Vector2;
import game.entities.projectiles.Projectile;
import game.entities.projectiles.ProjectilePattern;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileEnemy extends Entity {
    private final Player player;

    private ProjectilePattern projectilePattern;

    private ArrayList<Projectile> projectiles;

    public ProjectileEnemy(Scene scene, ProjectilePattern projectilePattern) {
        super(scene);

        this.player = scene.getEntity(Player.class);

        this.projectilePattern = projectilePattern;
        this.projectilePattern.attachTo(this);
        this.projectilePattern.active = true;

        this.projectiles = new ArrayList<>();
    }

    @Override
    public void onUpdate(double delta) {
        if(projectilePattern != null) projectilePattern.onUpdate(delta);
        for(int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.onUpdate(delta);
            if(player != null) {
                if(p.checkCollision(player.position, 5)) {
                    projectiles.remove(i);
                    player.takeDamage(p.getDamage());
                }
            }
        }
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect((int) position.x - 10, (int) position.y - 10, 20, 20);

        if(projectiles.isEmpty()) return;

        g2d.setColor(Color.CYAN);
        for(int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            g2d.fillOval(p.x, p.y, 5, 5);
        }
    }

    public void createProjectile(double direction, int speed) {
        double radians = Math.toRadians(direction - 90);
        double x = Math.cos(radians);
        double y = Math.sin(radians);

        projectiles.add(new Projectile(
                (int) position.x, (int) position.y,
                5, 5,
                0,
                new Vector2(x, y),
                speed)
        );
    }

    public Player getPlayer() {
        return scene.getEntity(Player.class);
    }
}
