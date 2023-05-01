import java.util.Random;
import java.util.Vector;

public class Bot extends Thread{
    private final String color;
    private final Board board;

    public Bot(String color, Board board) {
        this.board = board;
        this.color = color;
        System.out.println("Bot ma kolor: " + color);
    }

    @Override
    public void run(){

        Point StartingPoint;
        Point TargetPoint;

        while(Game.getGameStatus())
        {
            String move = Game.getMove();
            if(move.equals(color)) {
                Vector<CapturePath> Captures = board.getMandatoryMoves();
                if(Captures.size() != 0) {
                    int index = RandANumber(Captures.size());
                    CapturePath path = Captures.get(index);
                    Vector<Point> moves = path.getPath();

                    StartingPoint = path.getStartingPoint();
                    TargetPoint = moves.get(0);

                    board.MakeMove(StartingPoint, TargetPoint);
                } else{
                    Vector<Move> AvaiableMoves = board.getAvaiableMoves();
                    Move RandomMove = AvaiableMoves.get(RandANumber(AvaiableMoves.size()));

                    StartingPoint = new Point(RandomMove.getPosX(), RandomMove.getPosY());
                    TargetPoint = new Point(RandomMove.getTargetX(), RandomMove.getTargetY());


                    board.MakeMove(StartingPoint, TargetPoint);
                }
            }
            else{
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public int RandANumber(int max){
        Random random = new Random();

        return random.nextInt(max);
    }
}
