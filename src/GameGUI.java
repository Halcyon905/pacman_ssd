import game.Cell;
import game.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class GameGUI extends JFrame {
    private static final int CELL_SIZE = 10;
    private int mapHeight = 61;
    private int mapWidth = 55;
    private boolean toggleAnimation = false;
    private Game game;
    private GridUI gridUI;
    private PlayerInfo playerInfo;

    public GameGUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        game = new Game(CELL_SIZE);

        gridUI = new GridUI();
        playerInfo = new PlayerInfo(game.getLives(), mapWidth * CELL_SIZE);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        startGame();
    }

    public void startGame() {
        add(playerInfo, BorderLayout.NORTH);
        add(gridUI, BorderLayout.SOUTH);
        pack();

        while(true) {
            Thread gameThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    double timeStamp = System.currentTimeMillis();
                    toggleAnimation = false;
                    game.start();

                    for(int i = 3; i > 0; i--) {
                        try {
                            playerInfo.updateScore(i);
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    gridUI.requestFocus();

                    while(true) {
                        game.update();
                        playerInfo.updateScore(game.getScore());
                        playerInfo.updateLives(game.getLives());
                        if(System.currentTimeMillis() - timeStamp >= 210) {
                            timeStamp = System.currentTimeMillis();
                            toggleAnimation = !toggleAnimation;
                        }
                        repaint();
                        if(game.getGameState() == 2 || game.getGameState() == 3) {
                            break;
                        }
                        try {
                            sleep(42);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            gameThread.run();
            if(game.getGameState() == 3) {
                break;
            }
            game.reset();
            playerInfo.requestFocus();
        }
    }

    private class GridUI extends JPanel {
        private static final int PELLET_SIZE = 6;
        private static final double TURNING_WINDOW = 3.25;
        private static final int PAC_PADDING = 3;
        private static final int PELLET_PADDING = 2;
        private HashMap<String, Image> imageDirection = new HashMap<String, Image>();
        private HashMap<String, Image> blinkyImageDirection = new HashMap<>();
        private HashMap<String, Image> clydeImageDirection = new HashMap<String, Image>();
        private HashMap<String, Image> pinkyImageDirection = new HashMap<String, Image>();
        private Image imageClosed;
        private Image inkyImage;

        public GridUI() {
            setPreferredSize(new Dimension(mapWidth * CELL_SIZE, mapHeight * CELL_SIZE));
            imageDirection.put("N", new ImageIcon("img/pacman_north.png").getImage());
            imageDirection.put("S", new ImageIcon("img/pacman_south.png").getImage());
            imageDirection.put("W", new ImageIcon("img/pacman_west.png").getImage());
            imageDirection.put("E", new ImageIcon("img/pacman_east.png").getImage());
            imageClosed = new ImageIcon("img/pacman_closed.png").getImage();

            blinkyImageDirection.put("N", new ImageIcon("img/blinky/blinky_north.png").getImage());
            blinkyImageDirection.put("S", new ImageIcon("img/blinky/blinky_south.png").getImage());
            blinkyImageDirection.put("W", new ImageIcon("img/blinky/blinky_west.png").getImage());
            blinkyImageDirection.put("E", new ImageIcon("img/blinky/blinky_east.png").getImage());

            clydeImageDirection.put("N", new ImageIcon("img/clyde/clyde_north.png").getImage());
            clydeImageDirection.put("S", new ImageIcon("img/clyde/clyde_south.png").getImage());
            clydeImageDirection.put("W", new ImageIcon("img/clyde/clyde_west.png").getImage());
            clydeImageDirection.put("E", new ImageIcon("img/clyde/clyde_east.png").getImage());

            pinkyImageDirection.put("N", new ImageIcon("img/pinky/pinky_north.png").getImage());
            pinkyImageDirection.put("S", new ImageIcon("img/pinky/pinky_south.png").getImage());
            pinkyImageDirection.put("W", new ImageIcon("img/pinky/pinky_west.png").getImage());
            pinkyImageDirection.put("E", new ImageIcon("img/pinky/pinky_east.png").getImage());

            inkyImage = new ImageIcon("img/inky.png").getImage();

            getInputMap().put(KeyStroke.getKeyStroke("W"), "w pressed");
            getInputMap().put(KeyStroke.getKeyStroke("A"), "a pressed");
            getInputMap().put(KeyStroke.getKeyStroke("S"), "s pressed");
            getInputMap().put(KeyStroke.getKeyStroke("D"), "d pressed");
            Action moveNorth = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathYAxis();
                    // System.out.println("check path y axis");
                    if (result != -1) {
                        game.getPlayer().headNorth(result, game.getPlayer().getPositionY());
                    }
                }
            };
            Action moveSouth = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathYAxis();
                    if (result != -1) {
                        game.getPlayer().headSouth(result, game.getPlayer().getPositionY());
                    }
                }
            };
            Action moveEast = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathXAxis();
                    if (result != -1) {
                        game.getPlayer().headEast(game.getPlayer().getPositionX(), result);
                    }
                }
            };
            Action moveWest = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathXAxis();
                    if (result != -1) {
                        game.getPlayer().headWest(game.getPlayer().getPositionX(), result);
                    }
                }
            };
            getActionMap().put("w pressed", moveNorth);
            getActionMap().put("a pressed", moveWest);
            getActionMap().put("s pressed", moveSouth);
            getActionMap().put("d pressed", moveEast);
        }

        public int checkPathYAxis() {
            // checks if the player is close to a turning point,
            // where the player can change direction from moving horizontally to vertically.
            int col = game.getPlayer().getPositionX() / CELL_SIZE;
            int row = game.getPlayer().getPositionY() / CELL_SIZE;

            if (game.getPacmanMap().getCell(row, col).isTurning() && (col + 1) * CELL_SIZE - TURNING_WINDOW > game.getPlayer().getPositionX()) {
                return col * CELL_SIZE;
            } else if (game.getPacmanMap().getCell(row, col + 1).isTurning() &&
                    col * CELL_SIZE + TURNING_WINDOW < game.getPlayer().getPositionX()) {
                return (col + 1) * CELL_SIZE;
            }
            if (game.getPlayer().getHeading().equals("S") || game.getPlayer().getHeading().equals("N")) {
                return game.getPlayer().getPositionX();
            }
            // if player is not close to the turning point and currently heading west or east
            return -1;
        }

        public int checkPathXAxis() {
            // checks if the player is close to a turning point,
            // where the player can change direction from moving vertically to horizontally
            int col = game.getPlayer().getPositionX() / CELL_SIZE;
            int row = game.getPlayer().getPositionY() / CELL_SIZE;

            if (game.getPacmanMap().getCell(row, col).isTurning() && (row + 1) * CELL_SIZE - TURNING_WINDOW > game.getPlayer().getPositionY()) {
                return row * CELL_SIZE;
            } else if (game.getPacmanMap().getCell(row + 1, col).isTurning() &&
                    row * CELL_SIZE + TURNING_WINDOW < game.getPlayer().getPositionY()) {
                return (row + 1) * CELL_SIZE;
            }
            if (game.getPlayer().getHeading().equals("W") || game.getPlayer().getHeading().equals("E")) {
                return game.getPlayer().getPositionY();
            }
            return -1;
        }

        @Override
        public void paint(Graphics g) {
            for (int i = 0; i < mapHeight; i++) {
                for (int y = 0; y < mapWidth; y++) {
                    paintCell(g, i, y);
                }
            }
//            g.setColor(Color.red);
//            g.drawRect(game.getPlayer().getPositionX(), game.getPlayer().getPositionY(), CELL_SIZE * 3, CELL_SIZE * 3);

            g.drawImage(getPacmanImage(), game.getPlayer().getPositionX() + PAC_PADDING, game.getPlayer().getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);
            g.drawImage(getBlinkyImage(), game.getBlinky().getPositionX() + PAC_PADDING, game.getBlinky().getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);
            g.drawImage(inkyImage, game.getInky().getPositionX() + PAC_PADDING, game.getInky().getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);

            g.drawImage(getClydeImage(), game.getClyde().getPositionX() + PAC_PADDING, game.getClyde().getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);
            g.drawImage(getPinkyImage(), game.getPinky().getPositionX() + PAC_PADDING, game.getPinky().getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);

        }

        public Image getPacmanImage() {
            if (toggleAnimation) {
                return imageDirection.get(game.getPlayer().getHeading());
            }
            return imageClosed;
        }

        public Image getBlinkyImage() {
            return blinkyImageDirection.get(game.getBlinky().getHeading());
        }

        public Image getClydeImage() {
            return clydeImageDirection.get(game.getClyde().getHeading());
        }

        public Image getPinkyImage() {return pinkyImageDirection.get(game.getPinky().getHeading()); }

        public void paintCell(Graphics g, int row, int col) {
            Cell cell = game.getPacmanMap().getCell(row, col);
            int x = col * CELL_SIZE;
            int y = row * CELL_SIZE;

            if (cell.getWall()) {
                g.setColor(Color.blue);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            } else if (cell.isPelletStatus() && cell.getPellet() == 1) {
                g.setColor(Color.white);
                g.fillOval(x + PELLET_PADDING, y + PELLET_PADDING, PELLET_SIZE, PELLET_SIZE);
            } else if (cell.isPelletStatus() && cell.getPellet() == 2) {
                g.setColor(Color.white);
                g.fillOval(x + PELLET_PADDING - 3, y + PELLET_PADDING - 3, PELLET_SIZE + 3, PELLET_SIZE + 3);
            }
        }
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}
