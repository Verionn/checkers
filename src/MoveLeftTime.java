import java.io.Serializable;

public class MoveLeftTime implements Serializable {
    int redLeftTime;
    int whiteLeftTime;

    public MoveLeftTime(int redLeftTime, int whiteLeftTime) {
        this.redLeftTime = redLeftTime;
        this.whiteLeftTime = whiteLeftTime;
    }

    public int getRedLeftTime() {
        return redLeftTime;
    }

    public int getWhiteLeftTime() {
        return whiteLeftTime;
    }
}
