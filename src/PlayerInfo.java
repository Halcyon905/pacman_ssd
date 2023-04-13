import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel {
    private JLabel score;
    private JLabel lives;
    private Font font = new Font("TimesRoman", Font.PLAIN, 15);
    private Color fontColor = Color.white;

    public PlayerInfo(int startingLives) {
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);

        lives = new JLabel("Lives: " + startingLives);
        lives.setFont(font);
        lives.setForeground(fontColor);
        score = new JLabel("Score: 0");
        score.setFont(font);
        score.setForeground(fontColor);

        add(score);
        add(lives);
    }

    public void updateScore(int score) {
        this.score.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        this.lives.setText("Lives: " + lives);
    }
}
