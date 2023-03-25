import javax.swing.*;
import java.awt.*;

public class Board extends JPanel{

    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;
    private static final int FIELD_WIDTH = 80;
    private static final int FIELD_HEIGHT = 80;
    private static final int FIRST_FIELD_X = 180;
    private static final int FIRST_FIELD_Y = 180;
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    static int[][] Positions= {
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {3, 0, 3, 0, 3, 0, 3, 0},
            {0, 3, 0, 3, 0, 3, 0, 3},
            {3, 0, 3, 0, 3, 0, 3, 0}
    };
    //0 - banned
    //1 - empty
    //2 - player 1 - white
    //3 - player 2 - red
    public Board() {
        setBounds(180, 180, 640, 640);
        setBackground(Color.BLUE);
        setLayout(new GridLayout(ROWS, COLUMNS));
        for(int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                Field field;
                if ((i + j) % 2 == 0) {
                    field = new Field("WHITE");
                } else {
                    field = new Field("GREEN");
                }

                if (i < 3 && (i + j) % 2 == 1) {
                    Pawn pawn = new Pawn("WHITE");
                    field.add(pawn);
                }

                if (i > 4 && (i + j) % 2 == 1) {
                    Pawn pawn = new Pawn("RED");
                    field.add(pawn);
                }

                add(field);
            }
        }
    }
}
