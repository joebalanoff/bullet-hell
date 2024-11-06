package game.scenes.medieval.dungeon;

import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import game.entities.ProjectileEnemy;
import game.entities.projectiles.FollowProjectilePattern;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DungeonArea extends SceneArea {
    public Vector2[] corners;

    public DungeonArea(Scene scene) {
        super(scene);

        minPosition = new Vector2(0, 100);
        maxPosition = new Vector2(700, 800);

        corners = new Vector2[] {
                new Vector2(minPosition.x + 50, minPosition.y + 50),
                new Vector2(maxPosition.x - 50, minPosition.y + 50),
                new Vector2(maxPosition.x - 50, maxPosition.y - 50),
                new Vector2(minPosition.x + 50, maxPosition.y - 50),
        };

        ProjectileEnemy skeleton = new ProjectileEnemy(scene,
                new FollowProjectilePattern(0.8),
                corners
        );
        scene.addEntity(skeleton);

        skeleton = new ProjectileEnemy(scene,
                new FollowProjectilePattern(0.8),
                IntStream.range(0, corners.length)
                        .mapToObj(i -> corners[(i + 1) % corners.length])
                        .toArray(Vector2[]::new)
        );
        scene.addEntity(skeleton);

        skeleton = new ProjectileEnemy(scene,
                new FollowProjectilePattern(0.8),
                IntStream.range(0, corners.length)
                        .mapToObj(i -> corners[(i + 2) % corners.length])
                        .toArray(Vector2[]::new)
        );
        scene.addEntity(skeleton);

        skeleton = new ProjectileEnemy(scene,
                new FollowProjectilePattern(0.8),
                IntStream.range(0, corners.length)
                        .mapToObj(i -> corners[(i + 3) % corners.length])
                        .toArray(Vector2[]::new)
        );
        scene.addEntity(skeleton);
    }
}
