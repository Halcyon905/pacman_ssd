package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver  extends JPanel {
    private JPanel titlePanel;
    private JPanel scorePanel;
    private JPanel buttonPanel;
    private JLabel score;
    private JButton menu;
    private JButton exit;

    public GameOver(int width, int height) {
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width, height));

        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Game Over");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        title.setForeground(Color.white);
        titlePanel.add(title);

        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout());
        scorePanel.setBackground(Color.BLACK);
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        score = new JLabel();
        score.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        score.setForeground(Color.white);
        scorePanel.add(score);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        add(Box.createRigidArea(new Dimension(0, height/6)));
        add(titlePanel);
        add(Box.createRigidArea(new Dimension(0, height/6)));
        add(scorePanel);
        add(Box.createRigidArea(new Dimension(0, height/6)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, height/6)));
    }

    public void playerScore(int score) {
        this.score.setText("Score: " + score);
    }

}
