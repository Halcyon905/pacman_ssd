package game;

import entity.Entity;

public class Game {
    private static int CELL_SIZE;

    private Map pacmanMap;
    private Entity player;
    private int mapHeight = 61;
    private int mapWidth = 55;
    private int lives = 3;
    private int score = 0;
    private int gameState = 0;
    private int startX = 26;
    private int startY = 45;

    private double base_speed = 3;
    private boolean toggleAnimation = false;

    public Game(int cellSize) {
        CELL_SIZE = cellSize;

        pacmanMap = new Map(mapWidth, mapHeight);
        player = new Entity(26 * CELL_SIZE, 45 * CELL_SIZE, base_speed);
        //map1: 26, 45
        //map2: 26, 29
        //map3: 26, 57
    }

    public Entity getPlayer() {
        return player;
    }
    public Map getPacmanMap() {
        return pacmanMap;
    }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }
    public int getGameState() {
        return gameState;
    }

    public void start() {
        gameState = 1;
    }

    public void clearScore() {
        score = 0;
    }

    public void update() {
        updateMap();
        updatePlayer();
        if(pacmanMap.checkPelletOnMap()) {
            gameState = 2;
        }
    }

    public void updateMap() {
        int pellet = pacmanMap.updateCell((player.getPositionY() / CELL_SIZE) + 1,
                                        (player.getPositionX() / CELL_SIZE) + 1);
        if(pellet == 1) {
            score += 10;
        }
        else if(pellet == 2) {
            score += 50;
        }
    }

    public void reset(){
        pacmanMap.replaceAllPellet();
        player.headWest(startX * CELL_SIZE, startY * CELL_SIZE);
        gameState = 0;
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
}
