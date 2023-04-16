import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class MoveTimer extends JPanel {

    private static final int TIMER_HEIGHT = 40;
    private static final int TIMER_WIDTH = 150;
    private final JLabel TimeLabel;
    private int TimeLeft = 2;
    private final String PawnColor;

    public MoveTimer(int PosX, int PosY, String color){
        TimeLabel = new JLabel("Time: 5:00");
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        PawnColor = color;
        setBounds(PosX, PosY, TIMER_WIDTH, TIMER_HEIGHT);
        //setBackground(Color.gray);
        add(TimeLabel);
        StartTimer();
    }

    public void StartTimer() {

        ActionListener taskPerformer = evt -> {
            if(!BoardFrame.MOVE.equals(PawnColor) || !BoardFrame.GAME)
            {
                System.out.println("spamie");
                return;
            }
            TimeLeft--;
            int minute = TimeLeft / 60;
            int second = TimeLeft % 60;
            TimeLabel.setText("Time: " + minute + ":" + String.format("%02d", second));

            if (TimeLeft == 0) {
                BoardFrame.GAME = false;
                ((Timer)evt.getSource()).stop();
            }
        };

        Timer timer = new Timer(1000, taskPerformer);
        timer.start();
    }

}
