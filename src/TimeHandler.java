import java.io.IOException;
import java.io.ObjectOutputStream;

public class TimeHandler extends Thread{

    private final ObjectOutputStream objectOutputStream;
    private final Board board;
    private final Server server;

    public TimeHandler(ObjectOutputStream outputStream, Board board, Server server) {
        this.objectOutputStream = outputStream;
        this.board = board;
        this.server = server;
    }

    public void run(){

        while(Game.getGameStatus()){
            if(server.isDataSend()){
                int redLeftTime = board.getRedTimeLeft();
                int whiteLeftTime = board.getWhiteTimeLeft();
                MoveLeftTime moveTime = new MoveLeftTime(redLeftTime, whiteLeftTime);

                try {
                    objectOutputStream.writeObject(moveTime);
                    System.out.println("WYSŁAŁEM GODZINKE");
                    sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("jebac diska");
            }
        }
    }
}
