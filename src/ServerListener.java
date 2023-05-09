import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener extends Thread{
    private final ObjectInputStream objectInputStream;
    private final Player player;
    private final MoveTimer whiteTimer;
    private final MoveTimer redTimer;
    private final Screen screen;

    public ServerListener(ObjectInputStream objectInputStream, Player player, MoveTimer whiteTimer, MoveTimer redTimer, Screen screen) {
        this.objectInputStream = objectInputStream;
        this.player = player;
        this.whiteTimer = whiteTimer;
        this.redTimer = redTimer;
        this.screen = screen;
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
            if(receivedData instanceof BoardInfo){
                player.setPawn(((BoardInfo) receivedData).getPawn());
                player.setMove(((BoardInfo) receivedData).getMove());
                player.setColor(((BoardInfo) receivedData).getColor());
                player.repaint();
            }
            else if(receivedData instanceof MoveLeftTime){
                System.out.println("Otrzymalem godzinke");
                int whiteTimeLeft = ((MoveLeftTime) receivedData).getWhiteLeftTime();
                int redTimeLeft = ((MoveLeftTime) receivedData).getRedLeftTime();
                redTimer.updateTimer(redTimeLeft);
                whiteTimer.updateTimer(whiteTimeLeft);

                if(whiteTimeLeft == 0 || redTimeLeft == 0){
                    new EndGamePanel(screen, ((MoveLeftTime) receivedData).getWinner());
                }

                whiteTimer.repaint();
                redTimer.repaint();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
