import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {
    private static String TypeOfGame;
    private static final String NORMAL_GAME = "NORMAL";
    private static final String ONLINE_GAME = "ONLINE";
    private static final String BOT_GAME = "BOT";


    private static final String GAME_WITHOUT_BOT = "NONE";
    private static String MOVE = "WHITE";
    private static boolean GAME_STATUS = true;
    private static boolean NOT_CONNECTED = true;
    private static String WINNER;
    private String botColor;
    private static int WHITE_PAWNS;
    private static int RED_PAWNS;
    public static final int GAME_LENGTH = 200;


    public Game(String type) {
        setSettings();
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

    private void SelectMode() {
        Board board;
        Screen screen;
        if (TypeOfGame.equals(NORMAL_GAME)) {
            screen = new Screen();
            board = new Board(NORMAL_GAME, GAME_WITHOUT_BOT, screen);
            board.startTimer();
            screen.add(board);
        }
        if (TypeOfGame.equals(BOT_GAME)) {
            screen = new Screen();
            randBotColor();

            board = new Board(BOT_GAME, botColor, screen);
            board.startTimer();
            screen.add(board);

            Thread bot = new Bot(botColor, board);
            bot.start();

        } else if (TypeOfGame.equals(ONLINE_GAME)) {

            while (NOT_CONNECTED) {
                try {

                    Player player = new Player();
                    player.connectToServer();
                    NOT_CONNECTED = false;

                } catch (IOException e) {

                    Thread server = new Server();
                    server.start();

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException x) {
                        System.out.println("Interrupted! " + x);
                    }
                }
            }
        }
    }

    private void randBotColor() {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);

        if (RandomNumber == 0) {
            botColor = "RED";
        } else {
            botColor = "WHITE";
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

    public static void setGameStatus(boolean Game) {
        GAME_STATUS = Game;
    }

    public static void setMove(String Move) {
        MOVE = Move;
    }

    public static void setWinner(String Winner) {
        WINNER = Winner;
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
