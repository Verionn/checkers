import java.util.Random;

public class Game extends Thread{
    public static Player FirstPlayer;
    public static Player SecondPlayer;
    public static boolean GAME = true;
    public static String MOVE = "WHITE";
    public static String WINNER;

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
    }

    @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Watek zostal przerwany wziuuum");
            return;
        }
    }
}
