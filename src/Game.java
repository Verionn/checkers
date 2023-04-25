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
    private final BoardFrame boardFrame;
    private final Board board;

    private void RandomColors() {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);

        if(RandomNumber == 0) {
            FirstPlayer = new Player("RED", 3, false);
            SecondPlayer = new Player("WHITE", 2, false);
        }
        else
        {
            SecondPlayer = new Player("RED", 3, false);
            FirstPlayer = new Player("WHITE", 2, false);
        }
    }
    public Game(String type) {
        setSettings();
        RandomColors();
        boardFrame = new BoardFrame();
        board = new Board();
        boardFrame.add(board);
        TypeOfGame = type;
        SelectMode();
    }

    private void setSettings() {
        MOVE = "WHITE";
        WINNER = "DRAW";
        GAME = true;
        WHITE_PAWNS = 0;
        RED_PAWNS = 0;

    }
    private void SelectMode(){
        if (TypeOfGame.equals("NORMAL")) {
            return;
        }
        if(TypeOfGame.equals("BOT"))
        {
            Thread bot = new Bot(SecondPlayer.Color, board);
            bot.start();

        }
        else if(TypeOfGame.equals("ONLINE")){

        }
    }
}
