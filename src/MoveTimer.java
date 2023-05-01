import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MoveTimer extends JPanel {

    private static final int TIMER_HEIGHT = 40;
    private static final int TIMER_WIDTH = 150;
    private final JLabel TimeLabel;
    private final String PawnColor;
    private int TimeLeft;

    public MoveTimer(int PosX, int PosY, String color, int TimeInSec){
        TimeLabel = new JLabel("Time: 5:00");
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        PawnColor = color;
        setBounds(PosX, PosY, TIMER_WIDTH, TIMER_HEIGHT);
        add(TimeLabel);
        TimeLeft = TimeInSec;
        StartTimer();
    }

    public void StartTimer() {



        ActionListener taskPerformer = evt -> {

            if (TimeLeft == 0 || !Game.getGameStatus()) {
                Game.setGameStatus(false);
                ((Timer)evt.getSource()).stop();
                return;
            }

            if(Game.getMove().equals(PawnColor))
            {
                TimeLeft--;
                int minute = TimeLeft / 60;
                int second = TimeLeft % 60;
                TimeLabel.setText("Time: " + minute + ":" + String.format("%02d", second));
            }
        };

        Timer timer = new Timer(1000, taskPerformer);
        timer.start();
    }

}
