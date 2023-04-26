import ui.GameState;
import ui.Window;

import java.util.concurrent.TimeUnit;

public class MainLoop {

    private Window window;
    private GameState currentState;

    public MainLoop() {
        window = new Window();
        currentState = GameState.state;
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(43);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(currentState != GameState.state) {
                if (GameState.state == GameState.PLAYING) {
                    window.loadGameGUI();
                    currentState = GameState.state;
                }
                else if(GameState.state == GameState.SELECT) {
                    window.loadMapSelection();
                    currentState = GameState.state;
                }
                else if(GameState.state == GameState.EXIT) {
                    window.dispose();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new MainLoop();
    }
}
