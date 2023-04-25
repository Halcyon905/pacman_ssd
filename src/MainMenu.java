import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton play;
    private JButton exit;

    public MainMenu(int width, int height) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(width, height/2));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("PACMAN");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        title.setForeground(Color.white);
        titlePanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, height/2));
        buttonPanel.setBackground(Color.BLACK);

        play = new JButton("Play");
        play.setSize(new Dimension(50, 50));
        play.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        exit = new JButton("Exit");
        exit.setSize(new Dimension(50, 50));
        exit.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        buttonPanel.add(play);
        buttonPanel.add(exit);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

}
