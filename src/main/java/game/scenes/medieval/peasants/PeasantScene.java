package game.scenes.medieval.peasants;

import engine.scenes.Scene;
import game.entities.Player;

import java.awt.*;

public class PeasantScene extends Scene {
    public Player player;

    @Override
    public void onEnter() {
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
