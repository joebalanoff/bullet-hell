package game.entities.projectiles;

import game.entities.Player;

public class FollowProjectilePattern extends ProjectilePattern {
    private Player player;

    private double fireInterval;
    private double fireTimer = 0;

    public FollowProjectilePattern(double fireInterval) {
        this.fireInterval = fireInterval;
    }

    @Override
    public void onUpdate(double delta) {
        if(player == null) {
            player = attachedTo.getPlayer();
            if(player == null) return;
        }
        if(!active) return;
        double deltaX = player.position.x - attachedTo.position.x;
        double deltaY = player.position.y - attachedTo.position.y;

        double angleInRadians = Math.atan2(deltaY, deltaX);
        direction = Math.toDegrees(angleInRadians);

        fireTimer += delta;
        if(fireTimer > fireInterval) {
            fireTimer = 0;
            fire();
        }
    }

    @Override
    public void fire() {
        attachedTo.createProjectile(direction + 90, 500);
    }
}
