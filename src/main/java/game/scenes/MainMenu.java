package game.scenes;

import engine.scenes.Scene;
import game.entities.Player;

import java.awt.*;

public class MainMenu extends Scene {
    Player player;

    @Override
    public void onEnter() {
        player = addEntity(new Player(this));

        camera.setFollow(player.position);
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
