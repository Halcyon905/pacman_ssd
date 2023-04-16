package game;

import entity.Entity;

public class Game {
    private static int CELL_SIZE;

    private Map pacmanMap;
    private Entity player;
    private Entity blinky;
    private BlinkyAI blinkyAI;
    private int mapHeight = 61;
    private int mapWidth = 55;
    private int lives = 3;
    private int score = 0;
    private int gameState = 0;
    private int startX = 26;
    private int startY = 45;

    private double base_speed = 3;
    private double ghost_base_speed = 2;
    private boolean toggleAnimation = false;

    public Game(int cellSize) {
        CELL_SIZE = cellSize;

        pacmanMap = new Map(mapWidth, mapHeight);
        player = new Entity(26 * CELL_SIZE, 45 * CELL_SIZE, base_speed); //map1: 26, 45 / map2: 27, 29
        blinky = new Entity(11 * CELL_SIZE, 15 * CELL_SIZE, ghost_base_speed);
        blinkyAI = new BlinkyAI();
    }

    public Entity getPlayer() {
        return player;
    }
    public Map getPacmanMap() {
        return pacmanMap;
    }
    public Entity getBlinky() { return blinky; }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }
    public int getGameState() {
        return gameState;
    }
    public int getCellSize() {return CELL_SIZE; }

    public void start() {
        gameState = 1;
    }

    public void clearScore() {
        score = 0;
    }

    public void update() {
        updateMap();
        updatePlayer();
        updateBlinky();
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

    public void updateBlinky() {
        String nextMove = blinkyAI.getNextMove(getBlinky(), getPlayer(), this);
        // allow ghost to turn his head only in valid turning point
        if (nextMove.equals("N")) {
            int result = checkPathYAxisForGhost();
            if (result != -1){
                getBlinky().headNorth(result, getBlinky().getPositionY());
            }
        } else if (nextMove.equals("S")) {
            int result = checkPathYAxisForGhost();
            if (result != -1){
                getBlinky().headSouth(result, getBlinky().getPositionY());
            }
        } else if (nextMove.equals("E")) {
            int result = checkPathXAxisForGhost();
            if (result != -1){
                getBlinky().headEast(getBlinky().getPositionX(), result);
            }
        } else if (nextMove.equals("W")) {
            int result = checkPathXAxisForGhost();
            if (result != -1){
                getBlinky().headWest(getBlinky().getPositionX(), result);
            }
        }
        int checkCol = (getBlinky().getPositionX() / CELL_SIZE);
        int checkRow = (getBlinky().getPositionY() / CELL_SIZE);
        // manage pacman entering a teleporting gate
        // if player is on left edge, move player to the right edge facing leftward
        if(checkCol == 0) {
            getBlinky().headWest((mapWidth - 4) * CELL_SIZE, getBlinky().getPositionY());
            return;
        }
        // if player is on right edge, move player to the right edge facing rightward
        else if(checkCol == mapWidth - 3) {
            getBlinky().headEast(CELL_SIZE, getBlinky().getPositionY());
            return;
        }
        // if player is on the top edge (gate), move player to the bottom edge facing upward
        if(checkRow == 0) {
            getBlinky().headNorth(getBlinky().getPositionX(), (mapHeight - 4) * CELL_SIZE);
            return;
        }
        // if player is on the bottom edge (gate), move player to the top edge facing downward
        else if(checkRow == mapHeight - 3) {
            getBlinky().headSouth(getBlinky().getPositionX(), CELL_SIZE);
            return;
        }

        // preventing the ghost from moving through the walls
        if(getBlinky().getHeading().equals("N")) {
            int col = (int) (getBlinky().getPositionX() / CELL_SIZE);
            int row = (int) ((getBlinky().getPositionY() - ghost_base_speed) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                getBlinky().move();
            }
            return;
        }
        if(getBlinky().getHeading().equals("S")) {
            int col = (int) (getBlinky().getPositionX() / CELL_SIZE);
            int row = (int) ((getBlinky().getPositionY() + (CELL_SIZE * 3)) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                getBlinky().move();
            }
            return;
        }
        if(getBlinky().getHeading().equals("E")) {
            int col = (int) ((getBlinky().getPositionX() + (CELL_SIZE * 3)) / CELL_SIZE);
            int row = (int) (getBlinky().getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    // checking for right edge or right teleporting gate
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                getBlinky().move();
            }
            return;
        }
        if(getBlinky().getHeading().equals("W")) {
            int col = (int) ((getBlinky().getPositionX() - ghost_base_speed) / CELL_SIZE);
            int row = (int) (getBlinky().getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                getBlinky().move();
            }
        }
    }

    public int checkPathYAxisForGhost() {
        // checks if the ghost is close to a turning point,
        // where the ghost can change direction from moving horizontally to vertically.
        int col = getBlinky().getPositionX() / CELL_SIZE;
        int row = getBlinky().getPositionY() / CELL_SIZE;
        double TURNING_WINDOW = 3.25;

        if (getPacmanMap().getCell(row, col).isTurning() && (col + 1) * CELL_SIZE - TURNING_WINDOW > getBlinky().getPositionX()) {
            return col * CELL_SIZE;
        } else if (getPacmanMap().getCell(row, col + 1).isTurning() &&
                col * CELL_SIZE + TURNING_WINDOW < getBlinky().getPositionX()) {
            return (col + 1) * CELL_SIZE;
        }
        if (getBlinky().getHeading().equals("S") || getBlinky().getHeading().equals("N")) {
            return getBlinky().getPositionX();
        }
        // if ghost is not close to the turning point and currently heading south or north
        return -1;
    }

    public int checkPathXAxisForGhost() {
        // checks if the ghost is close to a turning point,
        // where the ghost can change direction from moving vertically to horizontally.
        int col = getBlinky().getPositionX() / CELL_SIZE;
        int row = getPlayer().getPositionY() / CELL_SIZE;
        double TURNING_WINDOW = 3.25;

        if (getPacmanMap().getCell(row, col).isTurning() && (row + 1) * CELL_SIZE - TURNING_WINDOW > getBlinky().getPositionY()) {
            return row * CELL_SIZE;
        } else if (getPacmanMap().getCell(row + 1, col).isTurning() &&
                row * CELL_SIZE + TURNING_WINDOW < getBlinky().getPositionY()) {
            return (row + 1) * CELL_SIZE;
        }
        if (getBlinky().getHeading().equals("W") || getPlayer().getHeading().equals("E")) {
            return getBlinky().getPositionY();
        }
        // if ghost is not close to the turning point and currently heading west or east
        return -1;
    }

    public void updatePlayer() {
        int checkCol = (player.getPositionX() / CELL_SIZE);
        int checkRow = (player.getPositionY() / CELL_SIZE);
        // manage pacman entering a teleporting gate
        // if player is on left edge, move player to the right edge facing leftward
        if(checkCol == 0) {
            player.headWest((mapWidth - 4) * CELL_SIZE, player.getPositionY());
            return;
        }
        // if player is on right edge, move player to the right edge facing rightward
        else if(checkCol == mapWidth - 3) {
            player.headEast(CELL_SIZE, player.getPositionY());
            return;
        }
        // if player is on the top edge (gate), move player to the bottom edge facing upward
        if(checkRow == 0) {
            player.headNorth(player.getPositionX(), (mapHeight - 4) * CELL_SIZE);
            return;
        }
        // if player is on the bottom edge (gate), move player to the top edge facing downward
        else if(checkRow == mapHeight - 3) {
            player.headSouth(player.getPositionX(), CELL_SIZE);
            return;
        }
        // preventing the player from moving through the walls
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
                    // checking for right edge or right teleporting gate
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
