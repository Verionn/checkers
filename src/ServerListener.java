import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener extends Thread{
    private final ObjectInputStream objectInputStream;
    private final Player player;
    private final MoveTimer whiteTimer;
    private final MoveTimer redTimer;

    public ServerListener(ObjectInputStream objectInputStream, Player player, MoveTimer whiteTimer, MoveTimer redTimer) {
        this.objectInputStream = objectInputStream;
        this.player = player;
        this.whiteTimer = whiteTimer;
        this.redTimer = redTimer;
    }

    public void run(){
        while(Game.getGameStatus()){
            getData();
            try {
                sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getData(){

        try{
            Object receivedData = objectInputStream.readObject();
            System.out.println("OTRZYMALEM OBIEKT");
            if(receivedData instanceof BoardInfo){
                System.out.println("OTRZYMALEM PLANSZE");
                player.setPawn(((BoardInfo) receivedData).getPawn());
                player.setMove(((BoardInfo) receivedData).getMove());
                player.setColor(((BoardInfo) receivedData).getColor());
                player.repaint();
            }
            else if(receivedData instanceof MoveLeftTime){
                System.out.println("OTRZYMALEM CZAS");
                redTimer.updateTimer(((MoveLeftTime) receivedData).getRedLeftTime());
                whiteTimer.updateTimer(((MoveLeftTime) receivedData).getWhiteLeftTime());
                whiteTimer.repaint();
                redTimer.repaint();
            }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        /*BoardInfo Data = null;
        try {
            Data = (BoardInfo) objectInputStream.readObject();
            System.out.println("ODEBRALEM DANE!");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        player.setPawn(Data.getPawn());
        player.setMove(Data.getMove());
        player.repaint();*/
    }
}
