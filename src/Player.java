public class Player extends Thread{
    final String Color;
    private final boolean isComputer;
    final int ID;
    public Pawn[] Figures = new Pawn[12];
    Player(String color, int id, boolean isComputer)
    {
        this.Color = color;
        this.ID = id;
        this.isComputer = isComputer;
    }

    public boolean isComputer() {
        return isComputer;
    }
}
