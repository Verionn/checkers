import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TimeHandler extends Thread{

    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Board board;
    private final Server server;

    public TimeHandler(ObjectOutputStream outputStream, ObjectInputStream objectInputStream, Board board, Server server) {
        this.objectOutputStream = outputStream;
        this.objectInputStream = objectInputStream;
        this.board = board;
        this.server = server;
    }

    public void run(){

        boolean sendingData = true;

        while(sendingData){
            if(server.isDataSend()){
                System.out.println("Wysyłam godzinke");
                int redLeftTime = board.getRedTimeLeft();
                int whiteLeftTime = board.getWhiteTimeLeft();
                PlayersTime moveTime = new PlayersTime(redLeftTime, whiteLeftTime);
                moveTime.setWinner(Game.getWinner());
                moveTime.setAllPlayers(Game.getGameStatus());
                if(!Game.getGameStatus()){
                    sendingData = false;
                }

                try {
                    objectOutputStream.writeObject(moveTime);
                    sleep(1000);
                } catch (IOException | InterruptedException e) {
                    System.out.println("Player left the game!");
                    break;
                }
            }
            else{
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try {
            objectOutputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Server disabled!");
        }

    }
}
