import javax.swing.*;
import java.awt.*;

public class Pawn extends JLabel{
    private final static String WHITE_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/White_Pawn.png";
    private final static String RED_PAWN_IMAGE_PATH = "/home/verion/Pulpit/PO2/VIII - Project/Checkers/src/Images/Red_Pawn.png";
    private final static int PAWN_SIZE = 65;
    private final String COLOR;
    private static final ImageIcon RedPawn = new ImageIcon(RED_PAWN_IMAGE_PATH);
    private static final ImageIcon WhitePawn = new ImageIcon(WHITE_PAWN_IMAGE_PATH);

    public Pawn(String color)
    {
        COLOR = color;
        if(color.equals("RED")) {
            setIcon(RedPawn);
        }
        else if(color.equals("WHITE")){
            setIcon(WhitePawn);
        }
        setPreferredSize(new Dimension(PAWN_SIZE, PAWN_SIZE));
        //setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
    }

    public String getCOLOR() {
        return COLOR;
    }
}