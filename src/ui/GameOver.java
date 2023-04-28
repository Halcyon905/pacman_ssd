package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver  extends JPanel {
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton menu;
    private JButton exit;
    public GameOver(int width, int height) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, (int) (height/1.5)));

        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(width, height/4));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("Game Over");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        title.setForeground(Color.white);
        titlePanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, height/4));
        buttonPanel.setBackground(Color.BLACK);

        menu = new JButton("Main Menu");
        menu.setSize(new Dimension(50, 50));
        menu.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState.state = GameState.MENU;
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

        buttonPanel.add(menu);
        buttonPanel.add(exit);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

}
