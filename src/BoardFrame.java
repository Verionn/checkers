import javax.swing.*;

public class BoardFrame extends JFrame {
    public static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;
    private static final int RED_TIMER_POS_X = 830;
    private static final int RED_TIMER_POS_Y = 200;
    private static final int WHITE_TIMER_POS_X = 830;
    private static final int WHITE_TIMER_POS_Y = 760;

    private final MoveTimer whiteTimer;
    private final MoveTimer redTimer;


    BoardFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Checkers");
        setResizable(false);
        setLayout(null);
        whiteTimer = new MoveTimer(WHITE_TIMER_POS_X, WHITE_TIMER_POS_Y, "WHITE", Game.GAME_LENGTH);
        redTimer = new MoveTimer(RED_TIMER_POS_X, RED_TIMER_POS_Y, "RED", Game.GAME_LENGTH);
        add(whiteTimer);
        add(redTimer);
        setVisible(true);
        WaitForEndOfTheGame(this);
    }

    public MoveTimer getWhiteTimer() {
        return whiteTimer;
    }

    public MoveTimer getRedTimer() {
        return redTimer;
    }

    public void WaitForEndOfTheGame(JFrame parent) {

        Timer timer = new Timer(1000, e -> {
            if(!Game.getGameStatus()) {
                new EndGamePanel(parent);
                ((Timer)e.getSource()).stop();

            }
        });
        timer.start();
    }
}
