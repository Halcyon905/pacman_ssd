package game.ai;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import entity.Entity;
import game.Game;
import game.Map;


public class InkyAI extends AI{
    private Entity currentTarget;
    private final int targetChangeInterval;
    private int updateCounter;

    public InkyAI() {
        currentTarget = null;
        targetChangeInterval=15;
        updateCounter = 0;
    }

    private Entity chooseNewTarget(Game game) {
        ArrayList<Entity> ghosts = new ArrayList<>();
        ghosts.add(game.getBlinky());
        ghosts.add(game.getClyde());
        Random random = new Random();
        int randomIndex = random.nextInt(ghosts.size());

        return ghosts.get(randomIndex);
    }

    @Override
    public String moveAI(Entity ghost, Entity pacman, Game game) {

        if (updateCounter % targetChangeInterval == 0) {
            currentTarget = chooseNewTarget(game);
        }
        updateCounter++;
        
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int targetCol = (int) (currentTarget.getPositionX() / game.getCellSize());
        int targetRow = (int) (currentTarget.getPositionY() / game.getCellSize());

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
}