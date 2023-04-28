package ui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private MainMenu mainMenu;
    private GameGUI gameGUI;
    private MapSelection mapSelection;
    private GameOver gameOver;

    public Window() {
        mainMenu = new MainMenu(565, 677);
        gameGUI = new GameGUI();
        mapSelection = new MapSelection(565, 677, gameGUI.game);
        gameOver = new GameOver(565, 677);

        loadMainMenu();

        setVisible(true);
        setSize(565, 677);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        repaint();
    }

    public void loadMainMenu() {
        remove(gameGUI);
        remove(gameOver);
        validate();

        getContentPane().setLayout(new BorderLayout());

        add(mainMenu, BorderLayout.SOUTH);
        pack();

        repaint();
    }

    public void loadMapSelection() {
        remove(mainMenu);
        validate();

        setSize(565, 677);
        getContentPane().setLayout(new BorderLayout());

        add(mapSelection, BorderLayout.SOUTH);
        pack();
        repaint();
    }

    public void loadGameGUI() {
        remove(mapSelection);
        validate();

        setLayout(new BorderLayout());
        add(gameGUI);
        pack();

        startGame();

        repaint();
    }

    public void loadGameOver() {
        remove(gameGUI);
        validate();

        getContentPane().setLayout(new BorderLayout());

        add(gameOver, BorderLayout.SOUTH);
        pack();

        repaint();
    }

    public void startGame() {
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                super.run();
                double timeStamp = System.currentTimeMillis();
                gameGUI.toggleAnimation = false;
                gameGUI.game.start();

                for(int i = 3; i > 0; i--) {
                    try {
                        gameGUI.playerInfo.updateScore(i);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                gameGUI.enableControls();

                while(true) {
                    gameGUI.game.update();
                    gameGUI.playerInfo.updateScore(gameGUI.game.getScore());
                    gameGUI.playerInfo.updateLives(gameGUI.game.getLives());
                    if(System.currentTimeMillis() - timeStamp >= 210) {
                        timeStamp = System.currentTimeMillis();
                        gameGUI.toggleAnimation = !gameGUI.toggleAnimation;
                    }
                    repaint();
                    if(gameGUI.game.getGameState() == 3) {
                        GameState.state = GameState.GAMEOVER;
                    }
                    try {
                        sleep(42);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        gameThread.start();
    }
}
