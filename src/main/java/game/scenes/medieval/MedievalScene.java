package game.scenes.medieval;

import engine.scenes.Scene;
import engine.scenes.SceneArea;
import engine.utils.Vector2;
import engine.listeners.Input;
import game.entities.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MedievalScene extends Scene {
    public Player player;
    SceneArea corridor1Horizontal;

    @Override
    public void onEnter() {
        SceneArea entrance = addArea(new SceneArea(this));
        entrance.minPosition = new Vector2(0, 100);
        entrance.maxPosition = new Vector2(700, 800);
        entrance.cameraZoom = 0.6f;

        corridor1Horizontal = addArea(new SceneArea(this));
        corridor1Horizontal.minPosition = new Vector2(700, 200);
        corridor1Horizontal.maxPosition = new Vector2(1500, 700);
        entrance.connectTo(corridor1Horizontal);

        SceneArea corridor1Vertical = addArea(new SceneArea(this));
        corridor1Vertical.minPosition = new Vector2(1200, -300);
        corridor1Vertical.maxPosition = new Vector2(1500, 200);
        corridor1Vertical.cameraZoom = 0.8f;
        corridor1Horizontal.connectTo(corridor1Vertical);

        SceneArea guardRoom = addArea(new SceneArea(this));
        guardRoom.minPosition = new Vector2(600, -700);
        guardRoom.maxPosition = new Vector2(1500, -300);
        corridor1Vertical.connectTo(guardRoom);

        SceneArea leftwardCorridor = addArea(new SceneArea(this));
        leftwardCorridor.minPosition = new Vector2(600, -1000);
        leftwardCorridor.maxPosition = new Vector2(1000, -700);
        leftwardCorridor.cameraZoom = 1f;
        guardRoom.connectTo(leftwardCorridor);

        SceneArea bossArena = addArea(new SceneArea(this));
        bossArena.minPosition = new Vector2(0, -1900);
        bossArena.maxPosition = new Vector2(1000, -1000);
        leftwardCorridor.connectTo(bossArena);

        player = addEntity(new Player(this));
    }

    @Override
    public void onUpdate(double delta) {
        if(Input.isKeyPressed(KeyEvent.VK_SPACE)) corridor1Horizontal.locked = !corridor1Horizontal.locked;
    }

    @Override
    public void onDraw(Graphics2D g2d) {

    }

    @Override
    public void onExit() {

    }
}