package game.ai;
import entity.Entity;
import game.Game;
import game.Map;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class AI {
    private int state = 0;
    private int slow = 0;

    public int getState() {
        return state;
    }

    public int getSlow() {
        return slow;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setSlow(int slow) {
        this.slow = slow;
    }

    public String getNextMove(Entity ghost, Entity pacman, Game game) {
        String nextMove = null;
        if (state == 0){
            nextMove = moveAI(ghost, pacman, game);
        } else if (state == 1) {
            nextMove = frightenedMove(ghost, pacman, game);
        }
        else if (state == 2) {
            nextMove = backSpawn(ghost, game);
        }
        return nextMove;
    }

    public String moveAI(Entity ghost, Entity pacman, Game game) {
        return null;
    }

    public String frightenedMove(Entity ghost, Entity pacman, Game game){
        // Get the current row and column of the ghost and pacman
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int pacmanCol = (int) (pacman.getPositionX() / game.getCellSize());
        int pacmanRow = (int) (pacman.getPositionY() / game.getCellSize());

        int rowDiff = Math.abs(pacmanRow - ghostRow);
        int colDiff = Math.abs(pacmanCol - ghostCol);
        // when ghost and pacman are near enough, ghost tried to escape
        if (rowDiff <= 30 && colDiff <= 30) {
            Random rand = new Random();
            int targetRow, targetCol;
            CellNode nextMove;
            // loop until target cell is far enough and reachable
            do {
                targetRow = rand.nextInt(game.getPacmanMap().getHeight());
                targetCol = rand.nextInt(game.getPacmanMap().getWidth());
                nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, targetRow, targetCol);
                rowDiff = Math.abs(pacmanRow - targetRow);
                colDiff = Math.abs(pacmanCol - targetCol);
            } while ((rowDiff <= 33 && colDiff <= 33) || nextMove == null);

            if (nextMove.row > ghostRow) {
                return "S";
            } else if (nextMove.row < ghostRow) {
                return "N";
            } else if (nextMove.col > ghostCol) {
                return "E";
            } else {
                return "W";
            }
        } return moveAI(ghost, pacman, game);
    }

    public String backSpawn(Entity ghost, Game game){
        // Get the current row and column of the ghost and spawn
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int spawnCol = game.getPacmanMap().getSpawnCol();
        int spawnRow = game.getPacmanMap().getSpawnRow();

        CellNode nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, spawnRow, spawnCol);
        if (nextMove != null) {
            if (nextMove.row > ghostRow){
                return "S";
            } else if (nextMove.row < ghostRow) {
                return "N";
            } else if (nextMove.col > ghostCol) {
                return "E";
            } else {return "W";}
        } else {
            System.out.println("I can't see!!");
            Random rand = new Random();
            String[] direction = {"N", "E", "W", "S"};
            int randomIndex = rand.nextInt(direction.length);
            return direction[randomIndex];
        }
    }

    public static class CellNode {
        int row;
        int col;
        CellNode previous;

        CellNode(int row, int col, CellNode previous){
            this.row = row;
            this.col = col;
            this.previous = previous;
        }
    }

    public CellNode bfs(Map map, int startRow, int startCol, int destRow, int destCol){
        Queue<CellNode> q = new LinkedList<>();
        Boolean[][] seen = new Boolean[map.getHeight()][map.getWidth()];
        // mark all cell as unvisited
        for (int mapRow = 0; mapRow < map.getHeight(); mapRow++){
            for (int mapCol=0; mapCol < map.getWidth(); mapCol++){
                seen[mapRow][mapCol] = false;
            }
        }
        CellNode startCellNode = new CellNode(startRow, startCol, null);
        q.add(startCellNode);
        seen[startCellNode.row][startCellNode.col] = true;

        while (!q.isEmpty()){
            CellNode v = q.peek();
            q.remove();

            // check if ghost is close to pacman
            int rowDiff = Math.abs(v.row - destRow);
            int colDiff = Math.abs(v.col - destCol);
            if (rowDiff <= 1 && colDiff <= 1) {
                if (!map.getCell(v.row, v.col).getWall() && !map.getCell(destRow, destCol).getWall()) {
                    // back track to get the nextMove from the path
                    CellNode nextMove = null;
                    while (v.previous != null) {
                        nextMove = v;
                        v = v.previous;
                    }
                    return nextMove;
                }
            }

            int [][] moveVariation = {{-1, 0}, {0, 3}, {3, 0}, {0, -1}};

            for (int moveIndex=0; moveIndex<4; moveIndex++){
                int possibleRow = v.row + moveVariation[moveIndex][0];
                int possibleCol = v.col + moveVariation[moveIndex][1];
                if (possibleRow >= 0 && possibleRow < map.getHeight() &&
                        possibleCol >= 0 && possibleCol < map.getWidth() &&
                        !seen[possibleRow][possibleCol] && !map.getCell(possibleRow, possibleCol).getWall()){
                    if (possibleRow < v.row && map.getCell(v.row-1, v.col).getWall()){
                        continue;
                    }
                    if (possibleRow > v.row && map.getCell(v.row+1, v.col).getWall()){
                        continue;
                    }
                    if (possibleCol < v.col && map.getCell(v.row, v.col-1).getWall()){
                        continue;
                    }
                    if (possibleCol > v.col && map.getCell(v.row, v.col+1).getWall()){
                        continue;
                    }
                    CellNode u = new CellNode(possibleRow, possibleCol, v);
                    q.add(u);
                    seen[u.row][u.col] = true;
                }
            }
        }
        return null;
    }
}