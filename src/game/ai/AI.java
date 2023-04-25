package game.ai;
import entity.Entity;
import game.Game;

public interface AI {
    String getNextMove(Entity ghost, Entity pacman, Game game);
}