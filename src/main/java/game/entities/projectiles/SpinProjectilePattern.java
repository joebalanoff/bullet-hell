package game.entities.projectiles;

public class SpinProjectilePattern extends ProjectilePattern {
    private double spinSpeed;
    private double fireInterval;

    private double fireTimer;

    public SpinProjectilePattern(double spinSpeed, double fireInterval) {
        this.spinSpeed = spinSpeed;
        this.fireInterval = fireInterval;
        this.lifeTime = 5;
    }

    @Override
    public void onUpdate(double delta) {
        direction += spinSpeed * delta;

        if(active) {
            fireTimer += delta;
            if (fireTimer > fireInterval) {
                fireTimer = 0;
                fire();
            }
        }
    }

    @Override
    public void fire() {
        attachedTo.createProjectile(direction, 500);
    }
}
