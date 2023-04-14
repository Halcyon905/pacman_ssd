package game;
import java.io.*;

public class Map {

    private Cell[][] cellArray;
    private int width;
    private int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        cellArray = new Cell[height][width];

        initCells();
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return null;
        }
        return cellArray[row][col];
    }

    private void initCells() {
        for(int i = 0; i < height; i++) {
            for(int y = 0; y < width; y ++) {
                cellArray[i][y] = new Cell();
            }
        }
    }

    public void setDefaultMap(String filePath) {
        try {
            FileReader fr = new FileReader(new File(filePath));
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            int row = 0;
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                int col = 0;
                for(String tempStr : tempArr) {
                    if(tempStr.equals("0")) {
                        cellArray[row][col].buildWall();
                    }
                    else if(tempStr.equals("1")) {
                        cellArray[row][col].setPellet();
                    }
                    else if(tempStr.equals("2")) {
                        cellArray[row][col].setPowerPellet();
                    }
                    else if(tempStr.equals("4")) {
                        cellArray[row][col].setTurning();
                    }
                    else {
                        cellArray[row][col].setEmpty();
                    }
                    col++;
                }
                row++;
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int updateCell(int row, int col) {
        Cell target = getCell(row, col);
        int result = 0;
        if (target.isPelletStatus()) {
            result = target.getPellet();
            target.eaten();
        }
        return result;
    }

    public boolean checkPelletOnMap() {
        Cell cell;
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                cell = getCell(row, col);
                if(cell.getPellet() > 0 && cell.isPelletStatus()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void replaceAllPellet() {
        Cell cell;
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                cell = getCell(row, col);
                if(cell.getPellet() > 0) {
                    cell.resetPellet();
                }
            }
        }
    }
}
