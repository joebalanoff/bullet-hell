import engine.core.Window;
import game.scenes.lobby.LobbyScene;
import game.scenes.medieval.MedievalScene;;

public class Main {
    public static void main(String[] args) {
       Window window = new Window(960, 540, "Bullet Hell");

       window.getSceneManager().addScene(new LobbyScene());
       window.getSceneManager().addScene(new MedievalScene());

       window.getSceneManager().setScene(1);
    }
}
