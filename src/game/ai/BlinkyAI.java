package game.ai;

import entity.Entity;
import game.Game;
import game.Map;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BlinkyAI extends AI {

    @Override
    public String moveAI(Entity ghost, Entity pacman, Game game) {
        // Get the current row and column of the ghost and pacman
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int pacmanCol = (int) (pacman.getPositionX() / game.getCellSize());
        int pacmanRow = (int) (pacman.getPositionY() / game.getCellSize());

        CellNode nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, pacmanRow, pacmanCol);
        if (nextMove != null) {
            if (nextMove.row > ghostRow){
                return "S";
            } else if (nextMove.row < ghostRow) {
                return "N";
            } else if (nextMove.col > ghostCol) {
                return "E";
            } else {return "W";}
        } else {
            // System.out.println("I can't see!!");
            Random rand = new Random();
            String[] direction = {"N", "E", "W", "S"};
            int randomIndex = rand.nextInt(direction.length);
            return direction[randomIndex];
        }
    }

}