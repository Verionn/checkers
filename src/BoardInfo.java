import java.io.Serializable;

public class BoardInfo implements Serializable {
    private Pawn[][] Pawn;
    private String Color;
    private String move;

    public BoardInfo() {
    }

    public Pawn[][] getPawn() {
        return Pawn;
    }

    public void setPawn(Pawn[][] pawn) {
        Pawn = pawn;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
