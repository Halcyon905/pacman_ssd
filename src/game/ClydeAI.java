package game;

import entity.Entity;
import java.util.Random;

public class ClydeAI implements AI{
    private int currentRandomIndex;
    private final int targetChangeInterval;
    private int updateCounter;

    public ClydeAI() {
        currentRandomIndex = 0;
        targetChangeInterval=2;
        updateCounter = 0;
    }

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game){
        String[] direction = {"N", "E", "W", "S"};
        Random rand = new Random();
        if (updateCounter % targetChangeInterval == 0) {
            currentRandomIndex = rand.nextInt(direction.length);
        }
        updateCounter++;
        return direction[currentRandomIndex];
    }
}
