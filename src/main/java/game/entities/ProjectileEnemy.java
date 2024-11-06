package game.entities;

import engine.scenes.Entity;
import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import game.entities.projectiles.Projectile;
import game.entities.projectiles.ProjectilePattern;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileEnemy extends Entity {
    private Player player;

    private ProjectilePattern projectilePattern;
    private ArrayList<Projectile> projectiles;

    private Vector2[] path;
    private int currentPathIndex;

    public ProjectileEnemy(Scene scene, ProjectilePattern projectilePattern, Vector2[] path) {
        super(scene);

        this.player = scene.getEntity(Player.class);

        this.projectilePattern = projectilePattern;
        this.projectilePattern.attachTo(this);
        this.projectilePattern.active = true;

        this.path = path;
        if(path.length > 0) {
            currentPathIndex = 0;
            this.position.x = path[0].x;
            this.position.y = path[0].y;
        }

        this.projectiles = new ArrayList<>();
    }

    @Override
    public void onUpdate(double delta) {
        handleMovement(delta);

        if(player == null) {
            player = scene.getEntity(Player.class);
            if(player == null) return;
        }
        if(projectilePattern != null) projectilePattern.onUpdate(delta);
        SceneArea activeArea = scene.getActiveArea();
        int minX = (int) activeArea.minPosition.x;
        int maxX = (int) activeArea.maxPosition.x;
        int minY = (int) activeArea.minPosition.y;
        int maxY = (int) activeArea.maxPosition.y;

        for(int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.onUpdate(delta);
            if(p.checkBounds(minX, minY, maxX, maxY)) {
                projectiles.remove(i);
                continue;
            }
            if(player != null) {
                if(p.checkCollision(player.position, 15)) {
                    projectiles.remove(i);
                    player.takeDamage(p.getDamage());
                }
            }
        }
    }

    private void handleMovement(double delta) {
        if (path == null || path.length == 0) return;

        Vector2 targetPoint = path[currentPathIndex];

        Vector2 direction = new Vector2(targetPoint.x - position.x, targetPoint.y - position.y);
        double distance = direction.getMagnitude();

        if (distance < 5) {
            currentPathIndex = (currentPathIndex + 1) % path.length;
            return;
        }

        direction.Normalize();

        double moveDistance = 100 * delta;

        if (moveDistance < distance) {
            position.x += direction.x * moveDistance;
            position.y += direction.y * moveDistance;
        } else {
            position.x = targetPoint.x;
            position.y = targetPoint.y;

            currentPathIndex = (currentPathIndex + 1) % path.length;
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
                10, 10,
                0,
                new Vector2(x, y),
                speed)
        );
    }

    public Player getPlayer() {
        return scene.getEntity(Player.class);
    }
}
