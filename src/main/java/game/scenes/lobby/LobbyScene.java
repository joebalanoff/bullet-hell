package game.scenes.lobby;

import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import game.entities.Player;

import java.awt.*;

public class LobbyScene extends Scene {
    private Player player;

    @Override
    public void onEnter() {
        SceneArea mainArea = addArea(new SceneArea(this));
        mainArea.minPosition = new Vector2(0, 0);
        mainArea.maxPosition = new Vector2(900, 500);
        mainArea.cameraZoom = 0.8f;

        player = addEntity(new Player(this));
        player.position = new Vector2(100, 250);

        SceneDoor medievalDoor = addEntity(new SceneDoor(this, 75, () -> {
            getSceneManager().transitionToScene(1);
        }));
        medievalDoor.position = new Vector2(200, 0);
    }

    @Override
    public void onUpdate(double delta) {

    }

    @Override
    public void onDraw(Graphics2D g2d) {

    }

    @Override
    public void onExit() {

    }
}
