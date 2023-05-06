import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerListener extends Thread{
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Player player;

    public ServerListener(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Player player) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.player = player;
    }

    public void run(){
        while(Game.getGameStatus()){
            getData();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getData() {
        BoardInfo Data = null;
        try {
            Data = (BoardInfo) objectInputStream.readObject();
            System.out.println("ODEBRALEM DANE!");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        player.setPawn(Data.getPawn());
        player.setMove(Data.getMove());
        player.repaint();
    }
}
