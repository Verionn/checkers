import java.util.Random;

public class Game {
    private static String TypeOfGame = "NORMAL";
    private static final String GameWithoutBot = "NONE";
    private Player FirstPlayer;
    private Player SecondPlayer;
    private static boolean GAME_STATUS = true;
    private static String MOVE = "WHITE";
    private static String WINNER;
    private static int WHITE_PAWNS;
    private static int RED_PAWNS;
    public static final int GAME_LENGTH = 300;
    private final BoardFrame boardFrame;

    private void RandomColors() {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);

        if(RandomNumber == 0) {
            FirstPlayer = new Player("RED");
            SecondPlayer = new Player("WHITE");
        }
        else
        {
            SecondPlayer = new Player("RED");
            FirstPlayer = new Player("WHITE");
        }
    }
    public Game(String type) {
        setSettings();
        RandomColors();
        boardFrame = new BoardFrame();
        TypeOfGame = type;
        SelectMode();
    }

    private void setSettings() {
        MOVE = "WHITE";
        WINNER = "DRAW";
        GAME_STATUS = true;
        WHITE_PAWNS = 0;
        RED_PAWNS = 0;

    }
    private void SelectMode(){
        Board board;
        if (TypeOfGame.equals("NORMAL")) {
            board = new Board("NORMAL", GameWithoutBot);
            boardFrame.add(board);
            return;
        }
        if(TypeOfGame.equals("BOT"))
        {
            board = new Board("BOT", SecondPlayer.getColor());
            boardFrame.add(board);
            Thread bot = new Bot(SecondPlayer.getColor(), board);
            bot.start();

        }
        else if(TypeOfGame.equals("ONLINE")){
            board = new Board("ONLINE", GameWithoutBot);
            boardFrame.add(board);

        }
    }

    public static String getMove() {
        return MOVE;
    }

    public static int getWhitePawns() {
        return WHITE_PAWNS;
    }

    public static int getRedPawns() {
        return RED_PAWNS;
    }

    public static void setGameStatus(boolean GAME) {
        Game.GAME_STATUS = GAME;
    }

    public static void setMove(String MOVE) {
        Game.MOVE = MOVE;
    }

    public static void setWinner(String WINNER) {
        Game.WINNER = WINNER;
    }

    public static void increaseRedPawns() {
        RED_PAWNS++;
    }

    public static void decreaseRedPawns() {
        RED_PAWNS--;
    }

    public static void increaseWhitePawns() {
        WHITE_PAWNS++;
    }

    public static void decreaseWhitePawns() {
        WHITE_PAWNS--;
    }

    public static boolean getGameStatus() {
        return GAME_STATUS;
    }

    public static String getWinner() {
        return WINNER;
    }
}
