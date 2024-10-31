package game.scenes.medieval.peasants;

import engine.scenes.Entity;
import engine.utils.Vector2;

import java.awt.*;
import java.util.HashMap;

public class Peasant extends Entity {
    private int health;
    private double speed;
    private HashMap<Vector2, Vector2> projectiles; // position, velocity

    public Peasant(PeasantScene scene) {
        super(scene);
        this.health = 3;
        this.speed = 1.5;

        this.projectiles = new HashMap<>();
        for(int i = 0; i < 15; i++) {
            projectiles.put(new Vector2(100, i * 50), new Vector2(2, 0));
        }
    }

    @Override
    public void onUpdate(double delta) {
        Vector2 targetPosition = ((PeasantScene) scene).player.position;
        for(Vector2 p : projectiles.keySet()) {
            p.Add(projectiles.get(p).multiply(delta));
        }
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillRoundRect((int) (position.x - 10), (int) (position.y - 10), 20, 20, 10, 10);
        g2d.setColor(Color.DARK_GRAY);
        for(Vector2 p : projectiles.keySet()) {
            g2d.fillOval((int) p.x, (int) p.y, 20, 20);
        }
    }
}
