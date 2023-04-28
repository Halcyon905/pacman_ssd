package game;

import entity.Entity;

public class PowerPelletState {
    public static boolean STATE = false;

    public static Entity checkCollision(Entity player, Entity ghost, Game game) {
        if(player.getPositionX() >= ghost.getPositionX() - game.getCellSize()
                && player.getPositionX() <= ghost.getPositionX() + game.getCellSize()
                && player.getPositionY() >= ghost.getPositionY() - game.getCellSize()
                && player.getPositionY() <= ghost.getPositionY() + game.getCellSize()){
            if(!STATE){
                return player;
            } else {
                return ghost;
            }
        }
        return null;
    }
}
