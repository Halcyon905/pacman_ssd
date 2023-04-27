package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelection extends JPanel {

    private JButton firstMap;
    private JButton secondMap;
    private Game game;

    public MapSelection(int width, int height, Game game) {
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));

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

        add(firstMap);
        add(secondMap);
    }
}
