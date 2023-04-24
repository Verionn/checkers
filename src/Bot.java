import java.util.Vector;

public class Bot extends Thread{
    private final String color;

    public Bot(String color) {
        this.color = color;
    }


    public void Play(){
        while(Game.GAME)
        {
            if(Game.MOVE.equals(color))
            {
                //Vector<Point> AvaiableMove
            }
        }
    }
}
