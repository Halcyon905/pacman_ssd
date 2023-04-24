package game;
import entity.Entity;

public interface AI {
    String getNextMove(Entity ghost, Entity pacman, Game game);
}