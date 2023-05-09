import javax.swing.*;

public class Screen extends JFrame {
    public static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;
    private static final int RED_TIMER_POS_X = 830;
    private static final int RED_TIMER_POS_Y = 200;
    private static final int WHITE_TIMER_POS_X = 830;
    private static final int WHITE_TIMER_POS_Y = 760;

    private final MoveTimer whiteTimer;
    private final MoveTimer redTimer;


    Screen(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Checkers");
        setBackground(Board.LightGray);
        setResizable(false);
        setLayout(null);
        whiteTimer = new MoveTimer(WHITE_TIMER_POS_X, WHITE_TIMER_POS_Y, Game.GAME_LENGTH);
        redTimer = new MoveTimer(RED_TIMER_POS_X, RED_TIMER_POS_Y, Game.GAME_LENGTH);
        add(whiteTimer);
        add(redTimer);
        setVisible(true);
    }

    public MoveTimer getWhiteTimer() {
        return whiteTimer;
    }

    public MoveTimer getRedTimer() {
        return redTimer;
    }
}
