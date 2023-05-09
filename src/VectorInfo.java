public class VectorInfo {
    private final int i; // index of the vector
    private final int j;// index of the last similar point

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public VectorInfo(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
