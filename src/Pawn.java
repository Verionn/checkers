/*import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;*/

public class Pawn{
    /*private final static String WHITE_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/White_Pawn.png";
    private final static String RED_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Red_Pawn.png";*/
    private static final int FIELD_SIZE = 80;
    private final String Color;

    /*private static final ImageIcon RedPawn = new ImageIcon(RED_PAWN_IMAGE_PATH);
    private static final ImageIcon WhitePawn = new ImageIcon(WHITE_PAWN_IMAGE_PATH);*/
    private boolean IsQueen = false;
    private int X;
    private int Y;

    public Pawn(String color, int x, int y, boolean isQueen)
    {
        X = x;
        Y = y;
        Color = color;
        IsQueen = isQueen;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public String getColor() {
        return Color;
    }

    public boolean isQueen() {
        return IsQueen;
    }

    public void CheckUpgrade()
    {
        if(Color.equals("RED"))
        {
            if(Y / FIELD_SIZE == 7)
            {
                System.out.println("CZERWONA DAMECZKA!");
                IsQueen = true;
            }
        }
        else
        {
            if(Y == 0)
            {
                System.out.println("BIALA DAMECZKA!");
                IsQueen = true;
            }
        }
    }
}