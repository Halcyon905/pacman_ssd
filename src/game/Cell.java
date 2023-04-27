package game;

public class Cell {
    private boolean wall;
    private int pellet;
    private boolean pelletStatus;
    private boolean turning;

    public Cell() {
        wall = false;
        turning = false;
        pelletStatus = false;
        pellet = 0;
    }

    public boolean getWall() {
        return wall;
    }

    public int getPellet() {
        return pellet;
    }

    public boolean isPelletStatus() {
        return pelletStatus;
    }

    public boolean isTurning() {
        return turning;
    }

    public void eaten() {
        if(pelletStatus) {
            pelletStatus = false;
        }
    }

    public void resetPellet() {
        pelletStatus = true;
    }

    public void setTurning() {
        turning = true;
    }

    public void setPellet() {
        pellet = 1;
        pelletStatus = true;
        wall = false;
        turning = false;
    }

    public void setPowerPellet() {
        pellet = 2;
        pelletStatus = true;
        wall = false;
        turning = false;
    }

    public void buildWall() {
        wall = true;
        pellet = 0;
        pelletStatus = false;
    }

    public void setEmpty() {
        wall = false;
        turning = false;
        pelletStatus = false;
        pellet = 0;
    }
}
