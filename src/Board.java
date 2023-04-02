import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements MouseListener, MouseMotionListener{
    private static final int FIELD_SIZE = 80;
    private static final int PAWN_OFFSET = 10;
    private static final int PAWN_SIZE = 60;
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private String MOVE = "WHITE";
    private final Pawn[][] PAWN = new Pawn[ROWS][COLUMNS];
    private int SELECTED_PAWN_X;
    private int SELECTED_PAWN_Y;

    private static final int[][] Positions= {
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
    //2 - red pawns
    //3 - white pawns
    public Board() {
        setBounds(180, 180, 640, 640);
        setLayout(null);
        AddPieces();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        PaintFields(g2d);
        PaintPawns(g2d);
    }
    private void PaintFields(Graphics g2d)
    {
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(i * FIELD_SIZE, j * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                } else {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(i * FIELD_SIZE, j * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                }
            }
        }
    }
    private void PaintPawns(Graphics g2d)
    {
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (PAWN[i][j] != null)
                {
                    if(PAWN[i][j].getColor().equals("RED"))
                    {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                    }
                    else
                    {
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                    }
                }
            }
        }
    }
    private void AddPieces()
    {
        for(int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                if (j < 3 && (i + j) % 2 == 1)
                {
                    PAWN[i][j] = new Pawn("RED", i * FIELD_SIZE, j * FIELD_SIZE);
                    new Pawn("RED", i, j);
                }
                if (j > 4 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("WHITE", i * FIELD_SIZE, j * FIELD_SIZE);
                }
            }
        }
    }
    private boolean ValidateMove(int x, int y, Pawn pawn)
    {
        if(!MOVE.equals(pawn.getColor()))
        {
            System.out.println("Ruch wykonał pion innego koloru");
            return false;
        }
        if(PAWN[x][y] != null)
        {
            System.out.println("Ruch w miejsce istniejacego juz pionka");
            return false;
        }
        if(Positions[x][y] == 0)
        {
            System.out.println("Ruch w miejsce zabronione");
            return false;
        }
        if(x == SELECTED_PAWN_X && y == SELECTED_PAWN_Y)
        {
            System.out.println("Ruch w te samo miejsce");
            return false;
        }
        if(pawn.getColor().equals("RED") && !pawn.isQueen())
        {
            if(SELECTED_PAWN_Y > y)
            {
                System.out.println("Ruch do tylu pionem czerwonym");
                return false;
            }
            if(y >= SELECTED_PAWN_Y + 2)
            {
                System.out.println(SELECTED_PAWN_Y + " | " + y );
                System.out.println("Ruch czerwonym pionem za daleko do przodu");
                return false;
            }

        }
        if(pawn.getColor().equals("WHITE") && !pawn.isQueen())
        {
            if(SELECTED_PAWN_Y < y)
            {
                System.out.println("Ruch do tylu pionem bialym");
                return false;
            }
            if(y <= SELECTED_PAWN_Y - 2)
            {
                System.out.println(SELECTED_PAWN_Y + " | " + y );
                System.out.println("Ruch bialym pionem za daleko do przodu");
                return false;
            }
        }

        return true;
    }
    private boolean CheckBeatings(Pawn pawn)
    {
        if(pawn.isQueen())
        {
            System.out.println("Jestem damka");
        }
        else
        {
            System.out.println("Jestem pionkiem");
            int x = pawn.getX();
            int y = pawn.getY();

        }
        return false;
    }
    private void ChangeMove() {
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        SELECTED_PAWN_X = e.getX()/FIELD_SIZE;
        SELECTED_PAWN_Y = e.getY()/FIELD_SIZE;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX()/FIELD_SIZE;
        int y = e.getY()/FIELD_SIZE;

        PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setX(x * FIELD_SIZE);
        PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setY(y * FIELD_SIZE);


        CheckBeatings(PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y]);

        if(ValidateMove(x, y, PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y]))
        {
            System.out.println("Prawidlowy ruch!");
            PAWN[x][y] = PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y];
            PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y] = null;
            ChangeMove();
        }
        else
        {
            PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setX(SELECTED_PAWN_X * FIELD_SIZE);
            PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setY(SELECTED_PAWN_Y * FIELD_SIZE);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setX(e.getX() - FIELD_SIZE / 2);
        PAWN[SELECTED_PAWN_X][SELECTED_PAWN_Y].setY(e.getY() - FIELD_SIZE / 2);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
