import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Pawn{
    private final static String WHITE_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/White_Pawn.png";
    private final static String RED_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Red_Pawn.png";
    private final String COLOR;
    private static final ImageIcon RedPawn = new ImageIcon(RED_PAWN_IMAGE_PATH);
    private static final ImageIcon WhitePawn = new ImageIcon(WHITE_PAWN_IMAGE_PATH);
    private int X;
    private int Y;

    public Pawn(String color, int x, int y)
    {
        X = x;
        Y = y;
        COLOR = color;
    }

    public String getCOLOR() {
        return COLOR;
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
}