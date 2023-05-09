import javax.swing.*;
import java.awt.*;

public class MoveTimer extends JPanel {

    private static final int TIMER_HEIGHT = 40;
    private static final int TIMER_WIDTH = 150;
    private final JLabel TimeLabel;
    private int TimeLeft;

    public MoveTimer(int PosX, int PosY, int TimeInSec){
        TimeLabel = new JLabel("");
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        setLocation(PosX, PosY);
        setSize(TIMER_WIDTH, TIMER_HEIGHT);
        setBackground(Color.yellow);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(TimeLabel);
        TimeLeft = TimeInSec;
        updateTimer(TimeLeft);
    }

    public void changePosition(int newPosX, int newPosY){
        setBounds(newPosX, newPosY, TIMER_WIDTH, TIMER_HEIGHT);
    }

    public void updateTimer(int newTimeLeft){
        TimeLeft = newTimeLeft;
        int minute = TimeLeft / 60;
        int second = TimeLeft % 60;
        TimeLabel.setText("Time: " + minute + ":" + String.format("%02d", second));
    }
}
