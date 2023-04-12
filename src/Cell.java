public class Cell {
    private boolean wall;
    private int pellet;

    private boolean turning;

    public Cell() {
        wall = false;
        turning = false;
        pellet = 0;
    }

    public boolean getWall() {
        return wall;
    }

    public int getPellet() {
        return pellet;
    }

    public boolean isTurning() {
        return turning;
    }

    public void eaten() {
        if(pellet != 0) {
            pellet = 0;
        }
    }

    public void setTurning() { turning = true; }

    public void setPellet() {
        pellet = 1;
    }

    public void setPowerPellet() {
        pellet = 2;
    }

    public void buildWall() {
        wall = true;
        pellet = 0;
    }
}
