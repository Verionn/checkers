import javax.swing.*;
import java.awt.*;

public class BoardFrame extends JFrame {
    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;
    BoardFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Checkers");
        setResizable(false);
        setLayout(null);
        add(new Board());
        //add(new Pawns());
        setVisible(true);
    }
}
