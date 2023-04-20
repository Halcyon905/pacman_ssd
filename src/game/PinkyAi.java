package game;

import entity.Entity;

import java.util.*;


public class PinkyAi implements AI{
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

    public PinkyAi(Map map){
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
        if (updateCounter % targetChangeInterval == 0 && getToPower) {
            updateCounter++;
            getToPower = false;
            currentTarget = chooseNewTarget();
        }
        if (getToPower){
            updateCounter++;
            if(ghost.getPositionY() != oldCol || ghost.getPositionX() != oldRow) {
                int randomIndex = rand.nextInt(direction.length);
                while (checkWall(ghost, direction[randomIndex], game.getPacmanMap(), game)) {
                    randomIndex = rand.nextInt(direction.length);
                }
                oldCol = ghost.getPositionY();
                oldRow = ghost.getPositionX();
            }
            return nextWay;
        }


        int ghostCol = (int) (ghost.getPositionX() / game.getCellSize());
        int ghostRow = (int) (ghost.getPositionY() / game.getCellSize());
        int targetCol = power.get(currentTarget).get(1);
        int targetRow = power.get(currentTarget).get(0);

        if (ghostRow == targetRow || ghostCol == targetCol){
            getToPower = true;
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
        boolean checkCell;
        if(direction.equals("N")){
            checkCell = map.getCell(row-1, col).getWall();
        } else if (direction.equals("S")){
            checkCell = map.getCell(row+3, col).getWall();
        } else if (direction.equals("E")){
            checkCell = map.getCell(row, col+3).getWall();
        } else{
            checkCell = map.getCell(row, col-1).getWall();
        }
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