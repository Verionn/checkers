import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements MouseListener {
    private static final int FIELD_SIZE = 80;
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private Field[][] FIELD = new Field[ROWS][COLUMNS];
    private Pawn[][] PAWN = new Pawn[ROWS][COLUMNS];
    private String MOVE = "WHITE";

    private static int[][] Positions= {
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
        addMouseListener(this);
        for(int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                if ((i + j) % 2 == 0) {
                    FIELD[i][j] = new Field("WHITE");
                } else {
                    FIELD[i][j] = new Field("GREEN");
                }

                if (i < 3 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("RED");
                    FIELD[i][j].add(PAWN[i][j]);
                }

                if (i > 4 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("WHITE");
                    FIELD[i][j].add(PAWN[i][j]);
                }
                add(FIELD[i][j]);
            }
        }
    }
    private void ChangeMove()
    {
        if(MOVE.equals("WHITE")) {
            MOVE = "RED";
        }
        else {
            MOVE = "WHITE";
        }
        System.out.println("RUCH: " + MOVE);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / FIELD_SIZE;
        int y = e.getY() / FIELD_SIZE;
        System.out.println("[" + y + " " + x + "]");
        Pawn selectedPawn = PAWN[y][x];
        System.out.println("RUCH: " + MOVE);
        if(selectedPawn != null)
        {
            if(selectedPawn.getCOLOR().equals(MOVE)) {
                System.out.println("Kliknales na pioneczka!!");
                ChangeMove();
            }
            else{
                System.out.println("Zly pioneczek byniu");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
