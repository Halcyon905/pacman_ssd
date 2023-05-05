package game;

import entity.Entity;

public class PowerPelletState {

    public static Entity checkCollision(Entity player, Entity ghost, Game game) {
        if(player.getPositionX() >= ghost.getPositionX() - game.getCellSize()
                && player.getPositionX() <= ghost.getPositionX() + game.getCellSize()
                && player.getPositionY() >= ghost.getPositionY() - game.getCellSize()
                && player.getPositionY() <= ghost.getPositionY() + game.getCellSize()){
            if(game.getGhostAI().get(ghost).getState() == 0){
                return player;
            } else {
                return ghost;
            }
        }
        return null;
    }
}
