import java.util.Random;

public class Game {
    private static String TypeOfGame = "NORMAL";
    private Player FirstPlayer;
    private Player SecondPlayer;
    public static boolean GAME = true;
    public static String MOVE = "WHITE";
    public static String WINNER;
    public static int WHITE_PAWNS;
    public static int RED_PAWNS;
    public static final int Game_Length = 300;
    private BoardFrame boardFrame;

    private void RandomColors() {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);

        if(RandomNumber == 0) {
            if(TypeOfGame.equals("BOT")) {
                FirstPlayer = new Player("RED", 3, true);
            }
            else{
                FirstPlayer = new Player("RED", 3, false);
            }
            SecondPlayer = new Player("WHITE", 2, false);
        }
        else
        {
            if(TypeOfGame.equals("BOT")) {
                SecondPlayer = new Player("WHITE", 3, true);
            }
            else {
                SecondPlayer = new Player("WHITE", 3, false);
            }
            FirstPlayer = new Player("RED", 2, false);
        }
    }
    public Game(String type) {
        setSettings();
        RandomColors();
        boardFrame = new BoardFrame();
        Game.TypeOfGame = type;
    }

    private void setSettings() {
        MOVE = "WHITE";
        WINNER = "DRAW";
        GAME = true;
        WHITE_PAWNS = 0;
        RED_PAWNS = 0;

    }
    private void Run(){
        while(GAME) {
            if (TypeOfGame.equals("NORMAL")) {
                return;
            }
            if(TypeOfGame.equals("BOT"))
            {

            }
            else if(TypeOfGame.equals("ONLINE")){
                
            }
        }
    }
}
