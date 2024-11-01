package game.scenes.medieval.peasants;

import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import game.entities.Player;

import java.awt.*;

public class PeasantScene extends Scene {
    public Player player;

    @Override
    public void onEnter() {
        SceneArea entrance = addArea(new SceneArea(this));
        entrance.minPosition = new Vector2(0, 0);
        entrance.maxPosition = new Vector2(700, 500);

        SceneArea corridor1Horizontal = addArea(new SceneArea(this));
        corridor1Horizontal.minPosition = new Vector2(700, 200);
        corridor1Horizontal.maxPosition = new Vector2(1500, 700);
        entrance.connectTo(corridor1Horizontal);

        SceneArea corridor1Vertical = addArea(new SceneArea(this));
        corridor1Vertical.minPosition = new Vector2(1200, -300);
        corridor1Vertical.maxPosition = new Vector2(1500, 200);
        corridor1Horizontal.connectTo(corridor1Vertical);

        SceneArea guardRoom = addArea(new SceneArea(this));
        guardRoom.minPosition = new Vector2(600, -700);
        guardRoom.maxPosition = new Vector2(1500, -300);
        corridor1Vertical.connectTo(guardRoom);

        SceneArea leftwardCorridor = addArea(new SceneArea(this));
        leftwardCorridor.minPosition = new Vector2(600, -1000);
        leftwardCorridor.maxPosition = new Vector2(1000, -700);
        guardRoom.connectTo(leftwardCorridor);

        SceneArea bossArena = addArea(new SceneArea(this));
        bossArena.minPosition = new Vector2(200, -1600);
        bossArena.maxPosition = new Vector2(1000, -1000);
        leftwardCorridor.connectTo(bossArena);

        player = addEntity(new Player(this));
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
