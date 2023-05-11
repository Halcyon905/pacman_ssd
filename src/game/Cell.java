package game;

public class Cell {
    private boolean wall;
    private int pellet;
    private boolean pelletStatus;
    private boolean turning;
    private boolean spawn;

    public Cell() {
        wall = false;
        turning = false;
        pelletStatus = false;
        spawn = false;
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

    public boolean isSpawn() { return spawn; }

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

    public void setSpawn() { spawn = true; }

    public void setPellet() {
        pellet = 1;
        pelletStatus = true;
    }

    public void setPowerPellet() {
        pellet = 2;
        pelletStatus = true;
    }

    public void buildWall() {
        wall = true;
    }
}
