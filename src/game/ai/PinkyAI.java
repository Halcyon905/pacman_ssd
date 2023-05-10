package game.ai;

import entity.Entity;
import game.Game;
import game.Map;

import java.util.*;


public class PinkyAI extends AI{
    private final int targetChangeInterval;
    private ArrayList<ArrayList<Integer>> power = new ArrayList<ArrayList<Integer>>();
    private int currentTarget = 0;
    private int updateCounter = 1;
    private boolean getToPower = false;
    private final Random rand;
    private final String[] direction = {"N", "E", "W", "S"};
    private int oldCol = 0;
    private int oldRow = 0;
    private String nextWay;

    public PinkyAI(game.Map map){
        this.targetChangeInterval = 20;
        rand = new Random();
        int index = 0;
        for (int row = 0; row < map.getHeight(); row++){
            for (int col = 0; col < map.getWidth(); col++){
                if(map.getCell(row, col).getPellet() == 2){
                    power.add(new ArrayList<Integer>());
                    power.get(index).add(0, row-1);
                    power.get(index).add(1, col-1);
                    index++;
                }
            }
        }
    }

    private int chooseNewTarget() {
        return rand.nextInt(power.size());
    }

    @Override
    public String moveAI(Entity ghost, Entity pacman, Game game) {
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int targetCol = power.get(currentTarget).get(1);
        int targetRow = power.get(currentTarget).get(0);

        if (ghostRow == targetRow || ghostCol == targetCol){
            getToPower = true;
        }

        if (updateCounter % targetChangeInterval == 0 && getToPower) {
            updateCounter++;
            getToPower = false;
            currentTarget = chooseNewTarget();
        }
        if (getToPower) {
            updateCounter++;
            if (ghostCol != oldCol || ghostRow != oldRow) {
                int randomIndex = rand.nextInt(direction.length);
                while (checkWall(ghost, direction[randomIndex], game.getPacmanMap(), game)) {
                    randomIndex = rand.nextInt(direction.length);
                }
                oldCol = ghostCol;
                oldRow = ghostRow;
            }
            return nextWay;
        }

        CellNode nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, targetRow, targetCol);
        if (nextMove != null) {
            if (nextMove.row > ghostRow){
                return "S";
            } else if (nextMove.row < ghostRow) {
                return "N";
            } else if (nextMove.col > ghostCol) {
                return "E";
            } else {return "W";}
        } else {
            Random rand = new Random();
            String[] direction = {"N", "E", "W", "S"};
            int randomIndex = rand.nextInt(direction.length);
            return direction[randomIndex];
        }
    }
    private boolean checkWall(Entity ghost, String direction, game.Map map, Game game) {
        int col = ghost.getPositionX() / game.getCellSize();
        int row = ghost.getPositionY() / game.getCellSize();
        boolean checkCell = switch (direction) {
            case "N" -> map.getCell(row - 1, col).getWall();
            case "S" -> map.getCell(row + 3, col).getWall();
            case "E" -> map.getCell(row, col + 3).getWall();
            default -> map.getCell(row, col - 1).getWall();
        };
        if (!checkCell) {
            nextWay = direction;
            return false;
        } else {
            return true;
        }
    }
}