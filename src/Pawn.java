import java.io.Serializable;

public class Pawn implements Serializable, Cloneable {
    private static final int FIELD_SIZE = 80;
    private final String Color;

    private boolean IsQueen;
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

    public void CheckUpgrade() {
        if(Color.equals("RED")) {
            if(Y / FIELD_SIZE == 7) {
                IsQueen = true;
            }
        }
        else {
            if(Y == 0) {
                IsQueen = true;
            }
        }
    }

    @Override
    public Pawn clone() {
        try {
            return (Pawn) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}