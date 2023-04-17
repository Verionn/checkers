import java.util.Random;

public class Game {
    public static Player FirstPlayer;
    public static Player SecondPlayer;
    public static boolean GAME = true;
    public static String MOVE = "WHITE";
    public static String WINNER;
    public static int WHITE_PAWNS;
    public static int RED_PAWNS;
    public static final int Game_Length = 300;

    private static void RandomColors()
    {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);
        if(RandomNumber == 0)
        {
            FirstPlayer = new Player("RED", 3);
            SecondPlayer = new Player("WHITE", 2);
        }
        else
        {
            FirstPlayer = new Player("WHITE", 2);
            SecondPlayer = new Player("RED", 3);
        }
    }
    public Game() {
        setSettings();
        RandomColors();
        new BoardFrame();
    }

    private void setSettings() {
        MOVE = "WHITE";
        WINNER = "DRAW";
        GAME = true;
        WHITE_PAWNS = 0;
        RED_PAWNS = 0;

    }
}
