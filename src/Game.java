import java.util.Random;

public class Game {
    public static Player FirstPlayer;
    public static Player SecondPlayer;

    static void RandomColors()
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
    public static void Run(){
        RandomColors();
        Board.Run();
    }
}
