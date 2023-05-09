import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {
    private static String TypeOfGame = "NORMAL";
    private static final String GAME_WITHOUT_BOT = "NONE";
    private static String MOVE = "WHITE";
    private static final int MAX_PLAYERS = 2;
    private static boolean GAME_STATUS = true;
    private static boolean NOT_CONNECTED = true;
    private static String WINNER;
    private String botColor;
    private static int WHITE_PAWNS;
    private static int RED_PAWNS;
    public static final int GAME_LENGTH = 300;
    private BoardFrame boardFrame;
    private int onlinePlayers = 0;


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
        if (TypeOfGame.equals("NORMAL")) {
            boardFrame = new BoardFrame();
            board = new Board("NORMAL", GAME_WITHOUT_BOT);
            board.startTimer();
            boardFrame.add(board);
        }
        if(TypeOfGame.equals("BOT"))
        {
            boardFrame = new BoardFrame();
            randBotColor();

            board = new Board("BOT", botColor);
            board.startTimer();
            boardFrame.add(board);

            Thread bot = new Bot(botColor, board);
            bot.start();

        }
        else if(TypeOfGame.equals("ONLINE")){

            while(NOT_CONNECTED){
                try{

                    System.out.println("TWORZE GRACZA!");
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

        if(RandomNumber == 0) {
            botColor = "RED";
        }
        else {
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
