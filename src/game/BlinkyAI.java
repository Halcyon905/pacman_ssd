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
            System.out.println("Player row and col: " + pacmanRow + "  " + pacmanCol);
            System.out.println("Ghost row and col: " + ghostRow + "  " + ghostCol);
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

            if ((v.row == destRow && v.col == destCol && !map.getCell(v.row, v.col).getWall()) ||
                    (v.row == destRow && v.col-1 == destCol && !map.getCell(v.row, v.col-1).getWall()) ||
                    (v.row == destRow && v.col+1 == destCol && !map.getCell(v.row, v.col+1).getWall()) ||
                    (v.row-1 == destRow && v.col == destCol && !map.getCell(v.row-1, v.col).getWall()) ||
                    (v.row+1 == destRow && v.col == destCol && !map.getCell(v.row+1, v.col).getWall()) ||
                    (v.row-1 == destRow && v.col-1 == destCol && !map.getCell(v.row-1, v.col-1).getWall()) ||
                    (v.row-1 == destRow && v.col+1==destCol && !map.getCell(v.row-1, v.col+1).getWall()) ||
                    (v.row+1==destRow && v.col-1 == destCol && !map.getCell(v.row+1, v.col-1).getWall()) ||
                    (v.row+1==destRow && v.col+1==destCol && !map.getCell(v.row+1, v.col+1).getWall()))
            {
                CellNode nextMove = null;
                while ( v.previous!=null ) {
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
                    CellNode u = new CellNode(possibleRow, possibleCol, v);
                    q.add(u);
                    seen[u.row][u.col] = true;
                }
            }
        }
        return null;
    }
}