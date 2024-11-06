package game.entities.projectiles;

import game.entities.ProjectileEnemy;

public abstract class ProjectilePattern {
    protected ProjectileEnemy attachedTo;
    protected double direction;
    protected double lifeTime;

    public boolean active;

    public ProjectilePattern() {
        this.direction = 0;
        this.active = false;
    }

    public abstract void onUpdate(double delta);
    public abstract void fire();

    public void attachTo(ProjectileEnemy enemy) {
        this.attachedTo = enemy;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}

