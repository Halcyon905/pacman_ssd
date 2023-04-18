package game;

import entity.Entity;
import java.util.Random;

public class ClydeAI implements AI{

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game){
        Random rand = new Random();
        String[] direction = {"N", "E", "W", "S"};
        int randomIndex = rand.nextInt(direction.length);
        return direction[randomIndex];
    }
}