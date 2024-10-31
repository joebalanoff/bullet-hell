package engine.scenes;

import engine.utils.Vector2;

import java.awt.*;

public abstract class Entity {
   protected final Scene scene;
   public Vector2 position;

   public Entity(Scene scene) {
      this.scene = scene;
      this.position = new Vector2();
   }

   public abstract void onUpdate(double delta);
   public abstract void onDraw(Graphics2D g2d);

   public double getX() { return position.x; }
   public double getY() { return position.y; }
}
