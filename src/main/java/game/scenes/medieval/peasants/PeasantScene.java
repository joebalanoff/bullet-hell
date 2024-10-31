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
        SceneArea area = addArea(new SceneArea(this));
        area.minPosition = new Vector2(0,0);
        area.maxPosition = new Vector2(600,400);

        SceneArea area2 = addArea(new SceneArea(this));
        area2.minPosition = new Vector2(100,-400);
        area2.maxPosition = new Vector2(500,0);

        SceneArea area3 = addArea(new SceneArea(this));
        area3.minPosition = new Vector2(0,-1000);
        area3.maxPosition = new Vector2(600,-400);

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
