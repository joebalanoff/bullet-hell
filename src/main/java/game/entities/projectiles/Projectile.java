package game.entities.projectiles;

import engine.utils.Vector2;

public class Projectile {
    public int x, y, w, h;
    private int damage;
    private Vector2 direction;
    private int speed;

    public Projectile(int x, int y, int w, int h, int damage, Vector2 direction, int speed) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.damage = damage;
        this.direction = direction;
        this.speed = speed;
    }

    public Projectile() {
        this.x = 0;
        this.y = 0;
        this.w = 5;
        this.h = 5;
        this.damage = 0;
    }

    public void onUpdate(double delta) {
        this.x += (int) (direction.x * delta * speed);
        this.y += (int) (direction.y * delta * speed);
    }
}

