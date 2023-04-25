import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jframe = new JFrame();
        jframe.setSize(300, 300);

        jframe.add(new MainMenu(200, 100));

        jframe.setVisible(true);
        jframe.setBackground(Color.BLACK);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
