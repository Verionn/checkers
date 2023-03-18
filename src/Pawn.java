public class Pawn {
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
    public void Move(int x, int y)
    {
        this.PosX = x;
        this.PosY = y;
    }

}
