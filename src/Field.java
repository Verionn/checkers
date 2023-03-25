import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private static final int FIELD_SIZE = 80;
    public Field(String color)
    {
        if(color.equals("GREEN"))
        {
            setBackground(java.awt.Color.GREEN);
        }
        else
        {
            setBackground(java.awt.Color.WHITE);
        }
        setPreferredSize(new Dimension(FIELD_SIZE, FIELD_SIZE));
        setOpaque(true);
    }
}
