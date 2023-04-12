import java.util.HashMap;

public class Player {

    private HashMap<String, int[]> direction = new HashMap<String, int[]>();
    private int positionX;
    private int positionY;
    private String heading = "E";
    private double speed;

    public Player(int startX, int startY, double speed) {
        int[] north = {-1, 0};
        direction.put("N", north);
        int[] south = {1, 0};
        direction.put("S", south);
        int[] east = {0, 1};
        direction.put("E", east);
        int[] west = {0, -1};
        direction.put("W", west);

        positionX = startX;
        positionY = startY;
        this.speed = speed;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getHeading() {
        return heading;
    }

    public void headNorth(int posX, int posY) {
        heading = "N";
        positionX = posX;
        positionY = posY;
    }
    public void headSouth(int posX, int posY) {
        heading = "S";
        positionX = posX;
        positionY = posY;
    }
    public void headEast(int posX, int posY) {
        heading = "E";
        positionX = posX;
        positionY = posY;
    }
    public void headWest(int posX, int posY) {
        heading = "W";
        positionX = posX;
        positionY = posY;
    }


    public void move() {
        int[] movement = direction.get(heading);
        positionY += movement[0] * speed;
        positionX += movement[1] * speed;
    }
}
