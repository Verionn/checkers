import javax.swing.*;
import java.awt.*;

public class Pawn extends JButton{
    private static final String WHITE_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Red_Pawn.png";
    private static final String RED_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/White_Pawn.png";
    private static final int PAWN_HEIGHT = 65;
    private static final int PAWN_WIDTH = 65;
    private boolean Alive = true;
    private boolean CanMove = true;
    final String Color;
    private int PosX;
    private int PosY;
    Pawn(String color, int x, int y)
    {
        this.Color = color;
        this.PosX = x;
        this.PosY = y;
    }
    Pawn(String Color)
    {
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setText(null);
        setSize(10,10);

        ImageIcon NotScaledRedPawnIcon = new ImageIcon(RED_PAWN_IMAGE_PATH);
        Image RedPawnImg = NotScaledRedPawnIcon.getImage();
        Image ScaledRedPawnIcon = RedPawnImg.getScaledInstance(PAWN_WIDTH,PAWN_HEIGHT, Image.SCALE_SMOOTH);
        Icon RedPawnIcon = new ImageIcon(ScaledRedPawnIcon);

        ImageIcon NotScaledWhitePawnIcon = new ImageIcon(WHITE_PAWN_IMAGE_PATH);
        Image WhitePawnImg = NotScaledWhitePawnIcon.getImage();
        Image ScaledWhitePawnIcon = WhitePawnImg.getScaledInstance(PAWN_WIDTH,PAWN_HEIGHT, Image.SCALE_SMOOTH);
        Icon WhitePawnIcon = new ImageIcon(ScaledWhitePawnIcon);

        if(Color.equals("RED"))
        {
            this.Color = Color;
            setIcon(RedPawnIcon);
        }
        else if(Color.equals("WHITE"))
        {
            this.Color = Color;
            setIcon(WhitePawnIcon);
        }
        else
        {
            System.out.println("Wrong color!");
            this.Color = Color;
            System.exit(-1);
        }

    }

}
