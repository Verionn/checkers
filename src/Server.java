import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread{
    private static final String GAME_WITHOUT_BOT = "NONE";
    private static final int MAX_PLAYERS = 2;

    private final String[] playerColor = new String[MAX_PLAYERS];
    ConnectionInfo[] socket = new ConnectionInfo[MAX_PLAYERS];

    private boolean DataSend = false;
    private int onlinePlayers = 0;
    private final Board board;

    TimeHandler[] timeHandler = new TimeHandler[MAX_PLAYERS];

    private void randPlayersColors() {
        Random rand = new Random();
        int RandomNumber = rand.nextInt(2);

        if(RandomNumber == 0) {
            playerColor[0] = "RED";
            playerColor[1] = "WHITE";
        }
        else {
            playerColor[0] = "WHITE";
            playerColor[1] = "RED";
        }
    }

    public Server() {
        board = new Board("ONLINE", GAME_WITHOUT_BOT, null);
        randPlayersColors();
    }

    private int getPlayerID(String color){
        for (int i = 0 ; i< MAX_PLAYERS; i++){
            if(playerColor[i].equals(color)){
                return i;
            }
        }
        return -1;
    }

    public void run() {
        System.out.println("Creating server...");

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5036);
        } catch (IOException e) {
            System.out.println("Server already exist! Terminating...");
            return;
        }

        Socket connection;
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;

        while (true)
        {
            try
            {
                System.out.println("Waiting for connections...");
                connection = serverSocket.accept();
                System.out.println("A new client has connected : " + connection);

                objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
                objectInputStream = new ObjectInputStream(connection.getInputStream());

                socket[onlinePlayers] = new ConnectionInfo(objectInputStream, objectOutputStream, playerColor[onlinePlayers]);
                timeHandler[onlinePlayers] = new TimeHandler(objectOutputStream, board, this);

                onlinePlayers++;

                if(onlinePlayers == MAX_PLAYERS) {
                    break;
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        board.startTimer();
        for (int i = 0; i < MAX_PLAYERS; i++){
            timeHandler[i].start();
        }

        while (Game.getGameStatus()){
            System.out.println("GŁOWNA PETELKA");
            BoardInfo boardInfo = new BoardInfo();

            Pawn[][] pawns = board.getPAWN();
            Pawn[][] newPawns = new Pawn[8][8];

            for (int i = 0; i < pawns.length; i++) {
                for (int j = 0; j < pawns[i].length; j++) {
                    if(pawns[i][j] != null){
                        newPawns[i][j] = pawns[i][j].clone();
                    }
                }
            }

            boardInfo.setPawn(newPawns);

            try {
                for (int j = 0; j < MAX_PLAYERS; j++){
                    System.out.println("SEND DATA");
                    boardInfo.setColor(socket[j].getColor());
                    boardInfo.setMove(Game.getMove());
                    socket[j].getOutput().writeObject(boardInfo);
                    checkIfTheGameEnded(board);
                }

                DataSend = true;

                int playerID = getPlayerID(Game.getMove());
                Move move = (Move) socket[playerID].getInput().readObject();

                board.MakeMove(move.getStartingPoint(), move.getTargetPoint(), socket[playerID].getColor());
                System.out.println("SERVER: RUCH: " + Game.getMove());

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkIfTheGameEnded(Board actualBoard){
        if(actualBoard.getWhiteTimeLeft() == 0 || actualBoard.getRedTimeLeft() == 0){
            Game.setGameStatus(false);
            System.out.println("Zmieniam status serwera");
        }
    }

    public boolean isDataSend() {
        return DataSend;
    }
}