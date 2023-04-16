package game;

import entity.Entity;

public class BlinkyAI implements AI {

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game) {
        int ghostX = ghost.getPositionX() / game.getCellSize();
        int ghostY = ghost.getPositionY() / game.getCellSize();
        int pacmanX = pacman.getPositionX() / game.getCellSize();
        int pacmanY = pacman.getPositionY() / game.getCellSize();

        int dx = pacmanX - ghostX;
        int dy = pacmanY - ghostY;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                return "E";
            } else {
                return "W";
            }
        } else {
            if (dy > 0) {
                return "S";
            } else {
                return "N";
            }
        }
    }
}