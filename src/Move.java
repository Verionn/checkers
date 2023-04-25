public class Move {
    private final int PosX;
    private final int PosY;

    private final int TargetX;
    private final int TargetY;

    public Move(int posX, int posY, int targetX, int targetY) {
        PosX = posX;
        PosY = posY;
        TargetX = targetX;
        TargetY = targetY;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public int getTargetX() {
        return TargetX;
    }

    public int getTargetY() {
        return TargetY;
    }
}
