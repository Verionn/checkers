import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Board extends Game {
    private static final String BOARD_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Board.png";
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static JPanel boardPanel;
    private static JLabel[][] cells = new JLabel[ROWS][COLUMNS];

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
    static Pawn FindField(int ID, String color)
    {
        for(int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(Positions[i][j] == ID)
                {
                    return new Pawn(color, i, j);
                }
            }
        }
        return null;
    }
    static void FillFiugres(Player player){
        for (int i = 0; i < 12; i++)
        {
            player.Figures[i] = FindField(player.ID, player.Color);
        }
    }
    public static void Run()
    {
        RandomColors();
        //FillFields();

        JFrame frame = new JFrame("Checkers");
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setBackground(Color.gray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(BOARD_IMAGE_PATH);
        JLabel background = new JLabel(imageIcon);

        boardPanel = new JPanel(new GridLayout(ROWS, COLUMNS));
        boardPanel.setBackground(Color.gray);
        frame.add(boardPanel);

        frame.setContentPane(background);
        frame.setLayout(new GridBagLayout());

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0 ; j < COLUMNS; j++)
            {
                JLabel field = new JLabel();
                field.setOpaque(true);
                if ((i + j) % 2 == 0) {
                    field.setBackground(Color.WHITE);
                } else {
                    field.setBackground(Color.GREEN);
                }
                cells[i][j] = field;
                boardPanel.add(field);
            }
        }

        frame.setVisible(true);
    }


}
