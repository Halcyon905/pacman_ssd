package game;

import entity.Entity;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ClydeAI implements AI{

    private String oldWay = "";
    private String nextWay = "";
    private int oldCol = 0;
    private int oldRow = 0;

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game){
        int col = ghost.getPositionX() / game.getCellSize();
        int row = ghost.getPositionY() / game.getCellSize();
        if(col != oldCol || row != oldRow){
            Random rand = new Random();
            game.Map map = game.getPacmanMap();
            String[] direction = {"N", "E", "W", "S"};
            int randomIndex = rand.nextInt(direction.length);
            while (checkWall(ghost, direction[randomIndex], map, game)) {
                randomIndex = rand.nextInt(direction.length);
                System.out.println("Check");
            }
            oldCol = col;
            oldRow = row;
        }
        return nextWay;
    }

    private boolean checkWay(String direction){
        return (direction.equals(oldWay));
    }

    private boolean checkWall(Entity ghost, String direction, game.Map map, Game game){
        int col = ghost.getPositionX() / game.getCellSize();
        int row = ghost.getPositionY() / game.getCellSize();
        boolean checkCell;
        if(direction.equals("N")){
            checkCell = map.getCell(row-1, col).getWall();
        } else if (direction.equals("S")){
            checkCell = map.getCell(row+3, col).getWall();
        } else if (direction.equals("E")){
            checkCell = map.getCell(row, col+3).getWall();
        } else{
            checkCell = map.getCell(row, col-1).getWall();
        }
        if(!checkCell){
            nextWay = direction;
            System.out.println(nextWay + "-" + checkCell);
            return false;
        } else {
            System.out.println(direction);
            return true;
        }
    }
}