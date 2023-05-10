package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelection extends JPanel {

    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton firstMap;
    private JButton secondMap;
    private JButton thirdMap;
    private Game game;

    public MapSelection(int width, int height, Game game) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, (int) (height/1.5)));

        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(width, height/4));
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("Select Map");
        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        title.setForeground(Color.white);
        titlePanel.add(title);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width, height/4));
        buttonPanel.setBackground(Color.BLACK);

        this.game = game;
        firstMap = new JButton("map 1");
        firstMap.setSize(new Dimension(50, 50));
        firstMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.loadSelectedMap("src/mapLayout/pacman_map.csv", 1);
                GameState.state = GameState.PLAYING;
            }
        });
        secondMap = new JButton("map 2");
        secondMap.setSize(new Dimension(50, 50));
        secondMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.loadSelectedMap("src/mapLayout/pacman_map_2.csv", 2);
                GameState.state = GameState.PLAYING;
            }
        });

        thirdMap = new JButton("map 3");
        thirdMap.setSize(new Dimension(50, 50));
        thirdMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.loadSelectedMap("src/mapLayout/pacman_map_3.csv", 3);
                GameState.state = GameState.PLAYING;
            }
        });

        buttonPanel.add(firstMap);
        buttonPanel.add(secondMap);
      buttonPanel.add(thirdMap);
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}

