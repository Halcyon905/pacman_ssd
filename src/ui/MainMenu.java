package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton play;
    private JButton exit;

    public MainMenu(int width, int height) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, (int) (height/1.5)));

        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(width, height/4));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("PACMAN");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        title.setForeground(Color.white);
        titlePanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, height/4));
        buttonPanel.setBackground(Color.BLACK);

        play = new JButton("Play");
        play.setSize(new Dimension(50, 50));
        play.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState.state = GameState.SELECT;
            }
        });

        exit = new JButton("Exit");
        exit.setSize(new Dimension(50, 50));
        exit.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState.state = GameState.EXIT;
            }
        });

        buttonPanel.add(play);
        buttonPanel.add(exit);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

}
