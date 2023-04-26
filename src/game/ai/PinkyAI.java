package game.ai;

import entity.Entity;
import game.Game;
import game.Map;

import java.util.*;


public class PinkyAI implements AI{
    private final int targetChangeInterval;
    private ArrayList<ArrayList<Integer>> power = new ArrayList<ArrayList<Integer>>();
    private int currentTarget = 0;
    private int updateCounter = 1;
    private boolean getToPower = false;
    private final Random rand;
    private final String[] direction = {"N", "E", "W", "S"};
    private int oldCol = 0;
    private int oldRow = 0;
    private String nextWay;

    public PinkyAI(game.Map map){
        this.targetChangeInterval = 20;
        rand = new Random();
        int index = 0;
        for (int row = 0; row < map.getHeight(); row++){
            for (int col = 0; col < map.getWidth(); col++){
                if(map.getCell(row, col).getPellet() == 2){
                    power.add(new ArrayList<Integer>());
                    power.get(index).add(0, row-1);
                    power.get(index).add(1, col-1);
                    index++;
                }
            }
        }
    }

    private int chooseNewTarget() {
        return rand.nextInt(power.size());
    }

    @Override
    public String getNextMove(Entity ghost, Entity pacman, Game game) {
        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int targetCol = power.get(currentTarget).get(1);
        int targetRow = power.get(currentTarget).get(0);

        if (ghostRow == targetRow || ghostCol == targetCol){
            getToPower = true;
        }

        if (updateCounter % targetChangeInterval == 0 && getToPower) {
            updateCounter++;
            getToPower = false;
            currentTarget = chooseNewTarget();
        }
        if (getToPower) {
            updateCounter++;
            if (ghostCol != oldCol || ghostRow != oldRow) {
                int randomIndex = rand.nextInt(direction.length);
                while (checkWall(ghost, direction[randomIndex], game.getPacmanMap(), game)) {
                    randomIndex = rand.nextInt(direction.length);
                }
                oldCol = ghostCol;
                oldRow = ghostRow;
            }
            return nextWay;
        }

        CellNode nextMove = bfs(game.getPacmanMap(), ghostRow, ghostCol, targetRow, targetCol);
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
    private boolean checkWall(Entity ghost, String direction, game.Map map, Game game){
        int col = ghost.getPositionX() / game.getCellSize();
        int row = ghost.getPositionY() / game.getCellSize();
        boolean checkCell = switch (direction) {
            case "N" -> map.getCell(row - 1, col).getWall();
            case "S" -> map.getCell(row + 3, col).getWall();
            case "E" -> map.getCell(row, col + 3).getWall();
            default -> map.getCell(row, col - 1).getWall();
        };
        if(!checkCell){
            nextWay = direction;
            return false;
        } else {
            return true;
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
            Arrays.fill(seen[mapRow], false);
        }
        CellNode startCellNode = new CellNode(startRow, startCol, null);
        q.add(startCellNode);
        seen[startCellNode.row][startCellNode.col] = true;

        while (!q.isEmpty()){
            CellNode v = q.peek();
            q.remove();

            // check if ghost is close to other ghost
            int rowDiff = Math.abs(v.row - destRow);
            int colDiff = Math.abs(v.col - destCol);
            if (rowDiff <= 1 && colDiff <= 1){
                // back track to get the nextMove from the path
                CellNode nextMove = null;
                while (v.previous != null) {
                    nextMove = v;
                    v = v.previous;
                }
                return nextMove;
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