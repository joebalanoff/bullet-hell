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
        entrance.maxPosition = new Vector2(600, 400);

        SceneArea corridor1 = addArea(new SceneArea(this));
        corridor1.minPosition = new Vector2(600, 0);
        corridor1.maxPosition = new Vector2(1000, 400);
        entrance.connectTo(corridor1);

        SceneArea downwardHallway = addArea(new SceneArea(this));
        downwardHallway.minPosition = new Vector2(600, -400);
        downwardHallway.maxPosition = new Vector2(1000, 0);
        corridor1.connectTo(downwardHallway);

        SceneArea guardRoom = addArea(new SceneArea(this));
        guardRoom.minPosition = new Vector2(400, -800);
        guardRoom.maxPosition = new Vector2(1000, -400);
        downwardHallway.connectTo(guardRoom);

        SceneArea leftwardCorridor = addArea(new SceneArea(this));
        leftwardCorridor.minPosition = new Vector2(0, -800);
        leftwardCorridor.maxPosition = new Vector2(400, -400);
        guardRoom.connectTo(leftwardCorridor);

        SceneArea bossArena = addArea(new SceneArea(this));
        bossArena.minPosition = new Vector2(0, -1400);
        bossArena.maxPosition = new Vector2(600, -800);
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
