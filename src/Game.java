import entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Game extends JFrame {
    private static final int CELL_SIZE = 10;

    private Map pacmanMap;
    private Entity player;
    private GridUI gridUI;
    private PlayerInfo playerInfo;
    private int mapHeight = 61;
    private int mapWidth = 55;
    private int lives = 3;
    private int score = 0;
    private double base_speed = 3;
    private boolean toggleAnimation = false;

    public Game() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        pacmanMap = new Map(mapWidth, mapHeight);
        player = new Entity(26 * CELL_SIZE, 45 * CELL_SIZE, base_speed); //map1: 26, 45 / map2: 27, 29
        gridUI = new GridUI();

        playerInfo = new PlayerInfo(lives);

        add(playerInfo, BorderLayout.NORTH);
        add(gridUI, BorderLayout.SOUTH);
        pack();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                double timeStamp = System.currentTimeMillis();
                while(true) {
                    updatePlayer();
                    updateMap();
                    if(System.currentTimeMillis() - timeStamp >= 210) {
                        timeStamp = System.currentTimeMillis();
                        toggleAnimation = !toggleAnimation;
                    }
                    repaint();
                    try {
                        sleep(42);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        thread.run();
    }

    public void updateMap() {
        int pellet = pacmanMap.updateCell((player.getPositionY() / CELL_SIZE) + 1,
                                        (player.getPositionX() / CELL_SIZE) + 1);
        if(pellet == 1) {
            score += 10;
            playerInfo.updateScore(score);
        }
        else if(pellet == 2) {
            score += 50;
            playerInfo.updateScore(score);
        }
    }

    public void updatePlayer() {
        int checkCol = (player.getPositionX() / CELL_SIZE);
        int checkRow = (player.getPositionY() / CELL_SIZE);
        if(checkCol == 0) {
            player.headWest((mapWidth - 4) * CELL_SIZE, player.getPositionY());
            return;
        }
        else if(checkCol == mapWidth - 3) {
            player.headEast(CELL_SIZE, player.getPositionY());
            return;
        }
        if(checkRow == 0) {
            player.headNorth(player.getPositionX(), (mapHeight - 4) * CELL_SIZE);
            return;
        }
        else if(checkRow == mapHeight - 3) {
            player.headSouth(player.getPositionX(), CELL_SIZE);
            return;
        }

        if(player.getHeading().equals("N")) {
            int col = (int) (player.getPositionX() / CELL_SIZE);
            int row = (int) ((player.getPositionY() - base_speed) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("S")) {
            int col = (int) (player.getPositionX() / CELL_SIZE);
            int row = (int) ((player.getPositionY() + (CELL_SIZE * 3)) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("E")) {
            int col = (int) ((player.getPositionX() + (CELL_SIZE * 3)) / CELL_SIZE);
            int row = (int) (player.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("W")) {
            int col = (int) ((player.getPositionX() - base_speed) / CELL_SIZE);
            int row = (int) (player.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                player.move();
            }
        }
    }

    private class GridUI extends JPanel {
        private static final int PELLET_SIZE = 6;
        private static final double TURNING_WINDOW = 3.25;
        private static final int PAC_PADDING = 3;
        private static final int PELLET_PADDING = 2;
        private HashMap<String, Image> imageDirection = new HashMap<String, Image>();
        private Image imageClosed;

        public GridUI() {
            setPreferredSize(new Dimension(mapWidth * CELL_SIZE, mapHeight * CELL_SIZE));
            imageDirection.put("N", new ImageIcon("img/pacman_north.png").getImage());
            imageDirection.put("S", new ImageIcon("img/pacman_south.png").getImage());
            imageDirection.put("W", new ImageIcon("img/pacman_west.png").getImage());
            imageDirection.put("E", new ImageIcon("img/pacman_east.png").getImage());
            imageClosed = new ImageIcon("img/pacman_closed.png").getImage();

            getInputMap().put(KeyStroke.getKeyStroke("W"), "w pressed");
            getInputMap().put(KeyStroke.getKeyStroke("A"), "a pressed");
            getInputMap().put(KeyStroke.getKeyStroke("S"), "s pressed");
            getInputMap().put(KeyStroke.getKeyStroke("D"), "d pressed");
            Action moveNorth = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathNorth();
                    if(result != -1) {
                        player.headNorth(result, player.getPositionY());
                    }
                }
            };
            Action moveSouth = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathSouth();
                    if(result != -1) {
                        player.headSouth(result, player.getPositionY());
                    }
                }
            };
            Action moveEast = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathEast();
                    if(result != -1) {
                        player.headEast(player.getPositionX(), result);
                    }
                }
            };
            Action moveWest = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int result = checkPathWest();
                    if(result != -1) {
                        player.headWest(player.getPositionX(), result);
                    }
                }
            };
            getActionMap().put("w pressed", moveNorth);
            getActionMap().put("a pressed", moveWest);
            getActionMap().put("s pressed", moveSouth);
            getActionMap().put("d pressed", moveEast);
        }

        public int checkPathNorth() {
            int col = player.getPositionX() / CELL_SIZE;
            int row = player.getPositionY() / CELL_SIZE;

            if(pacmanMap.getCell(row, col).isTurning() && (col + 1) * CELL_SIZE - TURNING_WINDOW > player.getPositionX()) {
                return col * CELL_SIZE;
            }
            else if(pacmanMap.getCell(row, col + 1).isTurning() &&
                    col * CELL_SIZE < player.getPositionX()) {
                return (col + 1) * CELL_SIZE;
            }
            if(player.getHeading().equals("S")) {
                return player.getPositionX();
            }
            return -1;
        }
        public int checkPathSouth() {
            int col = player.getPositionX() / CELL_SIZE;
            int row = player.getPositionY() / CELL_SIZE;

            if(pacmanMap.getCell(row, col).isTurning() && (col + 1) * CELL_SIZE - TURNING_WINDOW > player.getPositionX()) {
                return col * CELL_SIZE;
            }
            else if(pacmanMap.getCell(row, col + 1).isTurning() &&
                    col * CELL_SIZE < player.getPositionX()) {
                return (col + 1) * CELL_SIZE;
            }
            if(player.getHeading().equals("N")) {
                return player.getPositionX();
            }
            return -1;
        }
        public int checkPathEast() {
            int col = player.getPositionX() / CELL_SIZE;
            int row = player.getPositionY() / CELL_SIZE;

            if(pacmanMap.getCell(row, col).isTurning() && (row + 1) * CELL_SIZE - TURNING_WINDOW > player.getPositionY()) {
                return row * CELL_SIZE;
            }
            else if(pacmanMap.getCell(row + 1, col).isTurning() &&
                    row * CELL_SIZE < player.getPositionY()) {
                return (row + 1) * CELL_SIZE;
            }
            if(player.getHeading().equals("W")) {
                return player.getPositionY();
            }
            return -1;
        }
        public int checkPathWest() {
            int col = player.getPositionX() / CELL_SIZE;
            int row = player.getPositionY() / CELL_SIZE;

            if(pacmanMap.getCell(row, col).isTurning() && (row + 1) * CELL_SIZE - TURNING_WINDOW > player.getPositionY()) {
                return row * CELL_SIZE;
            }
            else if(pacmanMap.getCell(row + 1, col).isTurning() &&
                    row * CELL_SIZE < player.getPositionY()) {
                return (row + 1) * CELL_SIZE;
            }
            if(player.getHeading().equals("E")) {
                return player.getPositionY();
            }
            return -1;
        }

        @Override
        public void paint(Graphics g) {
            for(int i = 0; i < mapHeight; i++) {
                for(int y = 0; y < mapWidth; y++) {
                    paintCell(g, i, y);
                }
            }
//            g.setColor(Color.red);
//            g.drawRect(player.getPositionX(), player.getPositionY(), CELL_SIZE * 3, CELL_SIZE * 3);

            g.drawImage(getPacmanImage(), player.getPositionX() + PAC_PADDING, player.getPositionY() + PAC_PADDING,
                    (CELL_SIZE * 3) - (PAC_PADDING * 2), (CELL_SIZE * 3) - (PAC_PADDING * 2),
                    null, null);
        }

        public Image getPacmanImage() {
            if(toggleAnimation) {
                return imageDirection.get(player.getHeading());
            }
            return imageClosed;
        }

        public void paintCell(Graphics g, int row, int col) {
            Cell cell = pacmanMap.getCell(row, col);
            int x = col * CELL_SIZE;
            int y = row * CELL_SIZE;

            if (cell.getWall()) {
                g.setColor(Color.blue);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
            else if(cell.getPellet() == 1) {
                g.setColor(Color.white);
                g.fillOval(x + PELLET_PADDING, y + PELLET_PADDING, PELLET_SIZE, PELLET_SIZE);
            }
            else if(cell.getPellet() == 2) {
                g.setColor(Color.white);
                g.fillOval(x + PELLET_PADDING - 3, y + PELLET_PADDING - 3, PELLET_SIZE + 3, PELLET_SIZE + 3);
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
