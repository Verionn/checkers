import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerListener extends Thread{
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final Player player;
    private final MoveTimer whiteTimer;
    private final MoveTimer redTimer;
    private final Screen screen;
    private final String color;
    private boolean listeningData = true;

    public ServerListener(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Player player, MoveTimer whiteTimer, MoveTimer redTimer, Screen screen, String color) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.player = player;
        this.whiteTimer = whiteTimer;
        this.redTimer = redTimer;
        this.screen = screen;
        this.color = color;
    }

    public void run(){
        while(listeningData){
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
            else if(receivedData instanceof PlayersTime){
                int whiteTimeLeft = ((PlayersTime) receivedData).getWhiteLeftTime();
                int redTimeLeft = ((PlayersTime) receivedData).getRedLeftTime();
                boolean allPlayers = ((PlayersTime) receivedData).isAllPlayers();
                redTimer.updateTimer(redTimeLeft);
                whiteTimer.updateTimer(whiteTimeLeft);

                if(whiteTimeLeft == 0 || redTimeLeft == 0 || !allPlayers){
                    objectOutputStream.close();
                    objectInputStream.close();
                    listeningData = false;
                    System.out.println("Odpalam to tutaj?");
                    new EndGamePanel(screen, ((PlayersTime) receivedData).getWinner(), objectInputStream, objectOutputStream);
                }

                whiteTimer.repaint();
                redTimer.repaint();
            }

        } catch (IOException | ClassNotFoundException e) {
            new EndGamePanel(screen, color  ,objectInputStream, objectOutputStream);
            System.out.println("Player left! AAAAAAAAAAAAAAAAAAAaaa");
            listeningData = false;
        }
    }
}
