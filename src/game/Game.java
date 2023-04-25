package game;

import entity.Entity;

import java.util.HashMap;

public class Game {
    private static int CELL_SIZE;

    private Map pacmanMap;
    private Entity player;
    private Entity blinky;
    private Entity inky;
    private Entity clyde;
    private Entity pinky;
    private HashMap<Entity, AI> ghostAI = new HashMap<Entity, AI>();
    private int mapHeight = 61;
    private int mapWidth = 55;
    private int lives = 3;
    private int score = 0;
    private int gameState = 0;
    private int startX = 26;
    private int startY = 45;

    private double base_speed = 4;
    private double ghost_base_speed = 3;
    private boolean toggleAnimation = false;

    public Game(int cellSize) {
        CELL_SIZE = cellSize;

        pacmanMap = new Map(mapWidth, mapHeight);
        pacmanMap.setDefaultMap("src/mapLayout/pacman_map.csv");
        player = new Entity(26 * CELL_SIZE, 45 * CELL_SIZE, base_speed); //map1: 26, 45 / map2: 27, 29
        blinky = new Entity(11 * CELL_SIZE, 27 * CELL_SIZE, ghost_base_speed);
        inky = new Entity(32 * CELL_SIZE, 45 * CELL_SIZE, ghost_base_speed);
        clyde = new Entity(26 * CELL_SIZE, 9 * CELL_SIZE, ghost_base_speed);
        pinky = new Entity(26 * CELL_SIZE, 21 * CELL_SIZE, ghost_base_speed);
        ghostAI.put(blinky, new BlinkyAI());
        ghostAI.put(inky, new InkyAI());
        ghostAI.put(clyde, new ClydeAI());
        ghostAI.put(pinky, new PinkyAi(pacmanMap));
    }

    public Entity getPlayer() {
        return player;
    }
    public Map getPacmanMap() {
        return pacmanMap;
    }
    public Entity getBlinky() { return blinky; }
    public Entity getInky() { return inky; }
    public Entity getClyde() {return clyde; }
    public Entity getPinky() {return pinky; }

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
        updateGhost(blinky);
        updateGhost(clyde);
        updateGhost(inky);
        updateGhost(pinky);
        if(pacmanMap.checkPelletOnMap()) {
            gameState = 2;
        }
    }

    public void reset(){
        pacmanMap.replaceAllPellet();
        player.headWest(startX * CELL_SIZE, startY * CELL_SIZE);
        gameState = 0;
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

    public void updateGhost(Entity ghost) {
        if (pacmanMap.getCell(
                ghost.getPositionY() / CELL_SIZE,
                ghost.getPositionX() / CELL_SIZE).isTurning()){
            String nextMove = ghostAI.get(ghost).getNextMove(ghost, getPlayer(), this);
            // allow ghost to turn his head only in valid turning point
            if (nextMove.equals("N")) {
                int result = checkPathYAxisForGhost(ghost);
                if (result != -1){
                    ghost.headNorth(result, ghost.getPositionY());
                }
            } else if (nextMove.equals("S")) {
                int result = checkPathYAxisForGhost(ghost);
                if (result != -1){
                    ghost.headSouth(result, ghost.getPositionY());
                }
            } else if (nextMove.equals("E")) {
                int result = checkPathXAxisForGhost(ghost);
                if (result != -1){
                    ghost.headEast(ghost.getPositionX(), result);
                }
            } else if (nextMove.equals("W")) {
                int result = checkPathXAxisForGhost(ghost);
                if (result != -1){
                    ghost.headWest(ghost.getPositionX(), result);
                }
            }
        }

        int checkCol = (ghost.getPositionX() / CELL_SIZE);
        int checkRow = (ghost.getPositionY() / CELL_SIZE);
        // manage pacman entering a teleporting gate
        // if player is on left edge, move player to the right edge facing leftward
        if(checkCol == 0) {
            ghost.headWest((mapWidth - 4) * CELL_SIZE, ghost.getPositionY());
            return;
        }
        // if player is on right edge, move player to the right edge facing rightward
        else if(checkCol == mapWidth - 3) {
            ghost.headEast(CELL_SIZE, ghost.getPositionY());
            return;
        }
        // if player is on the top edge (gate), move player to the bottom edge facing upward
        if(checkRow == 0) {
            ghost.headNorth(ghost.getPositionX(), (mapHeight - 4) * CELL_SIZE);
            return;
        }
        // if player is on the bottom edge (gate), move player to the top edge facing downward
        else if(checkRow == mapHeight - 3) {
            ghost.headSouth(ghost.getPositionX(), CELL_SIZE);
            return;
        }

        // preventing the ghost from moving through the walls
        if(ghost.getHeading().equals("N")) {
            int col = (int) (ghost.getPositionX() / CELL_SIZE);
            int row = (int) ((ghost.getPositionY() - ghost_base_speed) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                ghost.move();
            }
            return;
        }
        if(ghost.getHeading().equals("S")) {
            int col = (int) (ghost.getPositionX() / CELL_SIZE);
            int row = (int) ((ghost.getPositionY() + (CELL_SIZE * 3)) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                ghost.move();
            }
            return;
        }
        if(ghost.getHeading().equals("E")) {
            int col = (int) ((ghost.getPositionX() + (CELL_SIZE * 3)) / CELL_SIZE);
            int row = (int) (ghost.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    // checking for right edge or right teleporting gate
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                ghost.move();
            }
            return;
        }
        if(ghost.getHeading().equals("W")) {
            int col = (int) ((ghost.getPositionX() - ghost_base_speed) / CELL_SIZE);
            int row = (int) (ghost.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                ghost.move();
            }
        }
    }

    public int checkPathYAxisForGhost(Entity ghost) {
        // checks if the ghost is close to a turning point,
        // where the ghost can change direction from moving horizontally to vertically.
        int col = ghost.getPositionX() / CELL_SIZE;
        int row = ghost.getPositionY() / CELL_SIZE;
        double TURNING_WINDOW = 3.25;

        if (getPacmanMap().getCell(row, col).isTurning() && (col + 1) * CELL_SIZE - TURNING_WINDOW > ghost.getPositionX()) {
            return col * CELL_SIZE;
        } else if (getPacmanMap().getCell(row, col + 1).isTurning() &&
                col * CELL_SIZE + TURNING_WINDOW < ghost.getPositionX()) {
            return (col + 1) * CELL_SIZE;
        }
        if (ghost.getHeading().equals("S") || ghost.getHeading().equals("N")) {
            return ghost.getPositionX();
        }
        // if ghost is not close to the turning point and currently heading south or north
        return -1;
    }

    public int checkPathXAxisForGhost(Entity ghost) {
        // checks if the ghost is close to a turning point,
        // where the ghost can change direction from moving vertically to horizontally.
        int col = ghost.getPositionX() / CELL_SIZE;
        int row = ghost.getPositionY() / CELL_SIZE;
        double TURNING_WINDOW = 3.25;

        if (getPacmanMap().getCell(row, col).isTurning() && (row + 1) * CELL_SIZE - TURNING_WINDOW > ghost.getPositionY()) {
            return row * CELL_SIZE;
        } else if (getPacmanMap().getCell(row + 1, col).isTurning() &&
                row * CELL_SIZE + TURNING_WINDOW < ghost.getPositionY()) {
            return (row + 1) * CELL_SIZE;
        }
        if (ghost.getHeading().equals("W") || ghost.getHeading().equals("E")) {
            return ghost.getPositionY();
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