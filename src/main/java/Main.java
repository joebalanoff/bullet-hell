import engine.core.Window;
import game.scenes.medieval.peasants.PeasantScene;;

public class Main {
    public static void main(String[] args) {
       Window window = new Window(800, 500, "Bullet Hell");

       window.getSceneManager().addScene(new PeasantScene());

       window.getSceneManager().setScene(0);
    }
}
