import java.io.Serializable;

public class PlayersTime implements Serializable {
    private final int redLeftTime;
    private final int whiteLeftTime;
    private String Winner;
    private boolean AllPlayers;

    public PlayersTime(int redLeftTime, int whiteLeftTime) {
        this.redLeftTime = redLeftTime;
        this.whiteLeftTime = whiteLeftTime;
    }

    public int getRedLeftTime() {
        return redLeftTime;
    }

    public int getWhiteLeftTime() {
        return whiteLeftTime;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public boolean isAllPlayers() {
        return AllPlayers;
    }

    public void setAllPlayers(boolean playerLeft) {
        AllPlayers = playerLeft;
    }
}
