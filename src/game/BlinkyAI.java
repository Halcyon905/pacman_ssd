package game;

import entity.Entity;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BlinkyAI implements AI {

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game) {
        // Get the current row and column of the ghost and pacman
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int pacmanCol = (int) (pacman.getPositionX() / game.getCellSize());
        int pacmanRow = (int) (pacman.getPositionY() / game.getCellSize());

        CellNode nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, pacmanRow, pacmanCol);
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

    private static class CellNode {
        int row;
        int col;
        CellNode previous;

        CellNode(int row, int col, CellNode previous){
            this.row = row;
            this.col = col;
            this.previous = previous;
        }
    }

    private CellNode bfs(Map map, int startRow, int startCol, int destRow, int destCol){
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

            if (isCloseToPacman(map, destRow, destCol, v)) {
                // back track to get the nextMove from the path
                CellNode nextMove = null;
                while (v.previous != null) {
                    nextMove = v;
                    v = v.previous;
                }
                return nextMove;
            }

            int [][] moveVariation = {{-3, 0}, {0, 3}, {3, 0}, {0, -3}};

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

    private static boolean isCloseToPacman(Map map, int destRow, int destCol, CellNode v) {
        // check if v is close enough to pacman row and col
        boolean found = false;
        for (int rowOffset = -2; rowOffset <= 2; rowOffset++) {
            for (int colOffset = -2; colOffset <= 2; colOffset++) {
                int newRow = v.row + rowOffset;
                int newCol = v.col + colOffset;

                if (newRow >= 0 && newRow < map.getHeight() &&
                        newCol >= 0 && newCol < map.getWidth() &&
                        !map.getCell(newRow, newCol).getWall() &&
                        (newRow == destRow && newCol == destCol)) {
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        return found;
    }
}