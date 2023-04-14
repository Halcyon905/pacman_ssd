package game;

import entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Game {
    private static int CELL_SIZE;

    private Map pacmanMap;
    private Entity player;
    private int mapHeight = 61;
    private int mapWidth = 55;
    private int lives = 3;
    private int score = 0;
    private double base_speed = 3;
    private boolean toggleAnimation = false;

    public Game(int cellSize) {
        CELL_SIZE = cellSize;

        pacmanMap = new Map(mapWidth, mapHeight);
        player = new Entity(26 * CELL_SIZE, 45 * CELL_SIZE, base_speed); //map1: 26, 45 / map2: 27, 29
    }

    public Entity getPlayer() {
        return player;
    }
    public Map getPacmanMap() {
        return pacmanMap;
    }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }

    public void update() {
        updateMap();
        updatePlayer();
    }

    public void updateMap() {
        int pellet = pacmanMap.updateCell((player.getPositionY() / CELL_SIZE) + 1,
                                        (player.getPositionX() / CELL_SIZE) + 1);
        if(pellet == 1) {
            score += 10;
        }
        else if(pellet == 2) {
            score += 50;
        }
    }

    public void updatePlayer() {
        int checkCol = (player.getPositionX() / CELL_SIZE);
        int checkRow = (player.getPositionY() / CELL_SIZE);
        if(checkCol == 0) {
            player.headWest((mapWidth - 4) * CELL_SIZE, player.getPositionY());
            return;
        }
        else if(checkCol == mapWidth - 3) {
            player.headEast(CELL_SIZE, player.getPositionY());
            return;
        }
        if(checkRow == 0) {
            player.headNorth(player.getPositionX(), (mapHeight - 4) * CELL_SIZE);
            return;
        }
        else if(checkRow == mapHeight - 3) {
            player.headSouth(player.getPositionX(), CELL_SIZE);
            return;
        }

        if(player.getHeading().equals("N")) {
            int col = (int) (player.getPositionX() / CELL_SIZE);
            int row = (int) ((player.getPositionY() - base_speed) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("S")) {
            int col = (int) (player.getPositionX() / CELL_SIZE);
            int row = (int) ((player.getPositionY() + (CELL_SIZE * 3)) / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row, col + 2).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("E")) {
            int col = (int) ((player.getPositionX() + (CELL_SIZE * 3)) / CELL_SIZE);
            int row = (int) (player.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                player.move();
            }
            return;
        }
        if(player.getHeading().equals("W")) {
            int col = (int) ((player.getPositionX() - base_speed) / CELL_SIZE);
            int row = (int) (player.getPositionY() / CELL_SIZE);
            if (!pacmanMap.getCell(row, col).getWall() &&
                    !pacmanMap.getCell(row + 2, col).getWall()) {
                player.move();
            }
        }
    }
}
