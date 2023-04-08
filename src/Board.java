import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Vector;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private static final int FIELD_SIZE = 80;
    private static final int PAWN_OFFSET = 10;
    private final double DIFF_BETWEEN_FIELDS = ReturnDistanceBetweenPoints(new Point(2,2), new Point(4,4));
    private static final int PAWN_SIZE = 60;
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private String MOVE = "WHITE";
    private final Pawn[][] PAWN = new Pawn[ROWS][COLUMNS];
    private int SELECTED_PAWN_X;
    private int SELECTED_PAWN_Y;
    private Vector<Pawn> PossibleCapturesForAllPawns = new Vector<>();
    private Vector<Point> PossibleCapturesForSpecificPawn = new Vector<>();
    private Vector<Point> MandatoryMoves = new Vector<>();
    private Vector<CapturePathClass> AllOptionsOfCapturingForSpecificPawn = new Vector<>();

    private static final int[][] Positions = {
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {3, 0, 3, 0, 3, 0, 3, 0},
            {0, 3, 0, 3, 0, 3, 0, 3},
            {3, 0, 3, 0, 3, 0, 3, 0}
    };

    //0 - banned
    //1 - empty
    //2 - red pawns
    //3 - white pawns
    public Board() {
        setBounds(180, 180, 640, 640);
        setLayout(null);
        AddPieces();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        PaintFields(g2d);
        PaintPawns(g2d);
    }

    private void PaintFields(Graphics g2d) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(j * FIELD_SIZE, i * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                } else {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(j * FIELD_SIZE, i * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                }
            }
        }
    }

    private void PaintPawns(Graphics g2d) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (PAWN[i][j] != null) {
                    if (PAWN[i][j].getColor().equals("RED")) {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                    } else {
                        g2d.setColor(Color.WHITE);
                        g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                    }
                }
            }
        }
    }

    private void AddPieces() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i < 3 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("RED", j * FIELD_SIZE, i * FIELD_SIZE);
                }
                if (i > 4 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("WHITE", j * FIELD_SIZE, i * FIELD_SIZE);
                }
            }
        }
    }

    private boolean ValidateMove(int x, int y, Pawn pawn) {
        if (!MOVE.equals(pawn.getColor())) {
            System.out.println("Ruch wykonał pion innego koloru");
            return false;
        }
        if (x > 7 || y > 7 || x < 0 || y < 0) {
            System.out.println("Ruch poza mape");
            return false;
        }
        if (PAWN[y][x] != null) {
            System.out.println("Ruch w miejsce istniejacego juz pionka");
            return false;
        }
        if (Positions[y][x] == 0) {
            System.out.println("Ruch w miejsce zabronione");
            return false;
        }
        if (x == SELECTED_PAWN_X && y == SELECTED_PAWN_Y) {
            System.out.println("Ruch w te samo miejsce");
            return false;
        }
        if (pawn.getColor().equals("RED") && !pawn.isQueen()) {
            if (SELECTED_PAWN_Y >= y) {
                System.out.println("Ruch do tylu lub w bok pionem czerwonym");
                return false;
            }
            if (y >= SELECTED_PAWN_Y + 2) {
                System.out.println("Ruch czerwonym pionem za daleko do przodu");
                return false;
            }

        }
        if (pawn.getColor().equals("WHITE") && !pawn.isQueen()) {
            if (SELECTED_PAWN_Y <= y) {
                System.out.println("Ruch do tylu lub w bok pionem bialym");
                return false;
            }
            if (y <= SELECTED_PAWN_Y - 2) {
                System.out.println(SELECTED_PAWN_Y + " | " + y);
                System.out.println("Ruch bialym pionem za daleko do przodu");
                return false;
            }
        }

        return true;
    }

    private Vector<Pawn> CheckCaptures(String color) {
        Vector<Pawn> pawns = new Vector<>();
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (PAWN[i][j] != null) {
                    if (PAWN[i][j].getColor().equals(color)) {
                        if (PAWN[i][j].isQueen()) {
                            System.out.println("Jestem damka");
                        } else {
                            if (CheckIfPawnCanCapture(PAWN[i][j], PAWN)) {
                                pawns.add(PAWN[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return pawns;
    }

    private Point CheckLeftBottomCorner(Pawn pawn, int x, int y, Pawn[][] Pawn){
        if(x-1 >= 0 && y+1 <= 7 && Pawn[y+1][x-1] != null)
        {
            if(!Pawn[y+1][x-1].getColor().equals(pawn.getColor()))
            {
                if(x-2 >= 0 && y+2 <= 7 && Pawn[y+2][x-2] == null)
                {
                    return new Point(x-2, y+2);
                }
            }
        }
        return null;
    }

    private Point CheckRightBottomCorner(Pawn pawn, int x, int y, Pawn[][] Pawn){
        if(x+1 <= 7 && y+1 <= 7 && Pawn[y+1][x+1] != null)
        {
            if(!Pawn[y+1][x+1].getColor().equals(pawn.getColor()))
            {
                if(x+2 <= 7 && y+2 <= 7 && Pawn[y+2][x+2] == null)
                {
                    return new Point(x+2, y+2);
                }
            }
        }
        return null;
    }

    private Point CheckLeftTopCorner(Pawn pawn, int x, int y, Pawn[][] Pawn){
        if(y-1 >= 0 && x-1 >= 0 &&  Pawn[y-1][x-1] != null)
        {
            if(!Pawn[y-1][x-1].getColor().equals(pawn.getColor()))
            {
                if(x-2 >= 0 && y-2 >= 0 && Pawn[y-2][x-2] == null)
                {
                    return new Point(x-2, y-2);
                }
            }
        }
        return null;
    }

    private Point CheckRightTopCorner(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        if(x+1 <= 7 && y-1 >= 0 && Pawn[y-1][x+1] != null)
        {
            if(!Pawn[y-1][x+1].getColor().equals(pawn.getColor()))
            {
                if(x+2 <= 7 && y-2 >= 0 && Pawn[y-2][x+2] == null)
                {
                    return new Point(x+2, y-2);
                }
            }
        }
        return null;
    }

    private boolean ValidateCapture(Point first, Point second, Pawn[][] CopyOfPawns){
        int x1 = first.getX();
        int y1 = first.getY();
        int x2 = second.getX();
        int y2 = second.getY();

        if(PAWN[(y1+y2)/2][(x1+x2)/2] == null)
        {
            return false;
        }
        return true;
    }

    private VectorInfo ReturnVectorWithSpecficLastPoint(Point CheckedPoint, Pawn[][] CopyOfPawns) {
        for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++)
        {
            CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(i);
            if(Path.isFinished())
            {
                continue;
            }
            Point LastPoint = Path.getPath().lastElement();
            if (ReturnDistanceBetweenPoints(LastPoint, CheckedPoint) == DIFF_BETWEEN_FIELDS) {
                return new VectorInfo(i, -1);
            }
        }
        for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++)
        {
            CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(i);
            Vector<Point> vector = Path.getPath();
            for (int j = 0; j < vector.size()-1; j++) {
                Point point = vector.get(j);
                if(ReturnDistanceBetweenPoints(point, CheckedPoint) == DIFF_BETWEEN_FIELDS)
                {
                    if(ValidateCapture(point, CheckedPoint, CopyOfPawns)) { // tutaj jest jakas poprawka do zrobienia wariacie
                        return new VectorInfo(i, j);
                    }
                }
            }
        }
        return new VectorInfo(-1, -1);
    }

    private void CheckIfCaptureCreateNewPath(Point CheckedPoint, Pawn[][] CopyOfPawns) {
        if(AllOptionsOfCapturingForSpecificPawn.size() == 0) {
            Vector<Point> FirstPath = new Vector<>();
            FirstPath.add(CheckedPoint);
            CapturePathClass Path = new CapturePathClass(FirstPath, false);
            AllOptionsOfCapturingForSpecificPawn.add(Path);
            return;
        }
        VectorInfo PathIndex = ReturnVectorWithSpecficLastPoint(CheckedPoint, CopyOfPawns);
        if(PathIndex.getI() == -1 && PathIndex.getJ() == -1)
        {
            Vector<Point> NewPath = new Vector<>();
            NewPath.add(CheckedPoint);
            CapturePathClass Path = new CapturePathClass(NewPath, false);
            AllOptionsOfCapturingForSpecificPawn.add(Path);
        }
        else if(PathIndex.getI() >= 0 && PathIndex.getJ() == -1)
        {
            CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(PathIndex.getI());
            Vector<Point> FoundPath = Path.getPath();
            FoundPath.add(CheckedPoint);
            AllOptionsOfCapturingForSpecificPawn.remove(PathIndex.getI());
            AllOptionsOfCapturingForSpecificPawn.add(Path);
        }
        else if(PathIndex.getI() >= 0 && PathIndex.getJ() >= 0)
        {
            CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(PathIndex.getI());
            Vector<Point> FoundPath = Path.getPath();
            Vector<Point> NewPath = new Vector<>();
            for (int i = 0; i <= PathIndex.getJ(); i++)
            {
                NewPath.add(FoundPath.get(i));
            }
            NewPath.add(CheckedPoint);
            CapturePathClass NewPathClass = new CapturePathClass(NewPath, false);
            AllOptionsOfCapturingForSpecificPawn.add(NewPathClass);
        }
    }

    private void ReturnCapturesOfSpecificPawn(Pawn pawn, Point prevField, Pawn[][] CopyOfPawns) {

        int x = pawn.getX() / FIELD_SIZE;
        int y = pawn.getY() / FIELD_SIZE;

        Point leftTopCorner = CheckLeftTopCorner(pawn, x, y, CopyOfPawns);
        if(leftTopCorner != null ) {
            if(prevField.getX() != x - 2 || prevField.getY() != y - 2) {
                Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                PossibleCapturesForSpecificPawn.add(leftTopCorner);
                CheckIfCaptureCreateNewPath(leftTopCorner, LastCopyOfPawns);
                LastCopyOfPawns[y-1][x-1] = null;
                LastCopyOfPawns[y-2][x-2] = LastCopyOfPawns[y][x];
                LastCopyOfPawns[y][x] = null;
                ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(),x * FIELD_SIZE - 2 * FIELD_SIZE, y * FIELD_SIZE - 2 * FIELD_SIZE), new Point(x, y), LastCopyOfPawns);
            }
        }

        Point rightTopCorner = CheckRightTopCorner(pawn, x, y, CopyOfPawns);
        if(rightTopCorner != null ) {
            if(prevField.getX() != x + 2 || prevField.getY() != y - 2) {
                Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                PossibleCapturesForSpecificPawn.add(rightTopCorner);
                CheckIfCaptureCreateNewPath(rightTopCorner, LastCopyOfPawns);
                LastCopyOfPawns[y-1][x+1] = null;
                LastCopyOfPawns[y-2][x+2] = LastCopyOfPawns[y][x];
                LastCopyOfPawns[y][x] = null;
                ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(),x * FIELD_SIZE + 2 * FIELD_SIZE, y * FIELD_SIZE - 2 * FIELD_SIZE), new Point(x, y), LastCopyOfPawns);
            }
        }

        Point leftBottomCorner = CheckLeftBottomCorner(pawn, x, y, CopyOfPawns);
        if(leftBottomCorner != null) {
            if(prevField.getX() != x - 2 || prevField.getY() != y + 2) {
                Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                PossibleCapturesForSpecificPawn.add(leftBottomCorner);
                CheckIfCaptureCreateNewPath(leftBottomCorner, LastCopyOfPawns);
                LastCopyOfPawns[y+1][x-1] = null;
                LastCopyOfPawns[y+2][x-2] = LastCopyOfPawns[y][x];
                LastCopyOfPawns[y][x] = null;
                ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(),x * FIELD_SIZE - 2 * FIELD_SIZE, y * FIELD_SIZE + 2 * FIELD_SIZE), new Point(x, y), LastCopyOfPawns);
            }
        }

        Point rightBottomCorner = CheckRightBottomCorner(pawn, x, y, CopyOfPawns);
        if(rightBottomCorner != null) {
            if(prevField.getX() != x + 2 || prevField.getY() != y + 2) {
                Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                PossibleCapturesForSpecificPawn.add(rightBottomCorner);
                CheckIfCaptureCreateNewPath(rightBottomCorner, LastCopyOfPawns);
                LastCopyOfPawns[y+1][x+1] = null;
                LastCopyOfPawns[y+2][x+2] = LastCopyOfPawns[y][x];
                LastCopyOfPawns[y][x] = null;
                ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(),x * FIELD_SIZE + 2 * FIELD_SIZE, y * FIELD_SIZE + 2 * FIELD_SIZE), new Point(x, y), LastCopyOfPawns);
            }
        }
        if(!CheckIfPawnCanCapture(pawn, CopyOfPawns))
        {
            for (int i = 0 ; i < AllOptionsOfCapturingForSpecificPawn.size(); i++){
                CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(i);
                Vector<Point> CapturePath = Path.getPath();
                Point LastPoint = CapturePath.lastElement();
                if(LastPoint.getY() == y && LastPoint.getX() == x)
                {
                    Path.setFinished(true);
                    AllOptionsOfCapturingForSpecificPawn.remove(i);
                    AllOptionsOfCapturingForSpecificPawn.add(Path);
                }
            }
        }
    }

    private boolean CheckIfPawnCanCapture(Pawn pawn, Pawn[][] CopyOfPawns) {
        int y = pawn.getY() / FIELD_SIZE;
        int x = pawn.getX() / FIELD_SIZE;

        if(pawn.isQueen())
        {
            System.out.println("Jestem damka");
        }
        else
        {
            if(CheckLeftTopCorner(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if(CheckRightTopCorner(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if(CheckLeftBottomCorner(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            return CheckRightBottomCorner(pawn, x, y, CopyOfPawns) != null;
        }
        return false;
    }

    private void ChangeMove() {
        if(MOVE.equals("WHITE")) {
            MOVE = "RED";
        }
        else {
            MOVE = "WHITE";
        }
        System.out.println("RUCH: " + MOVE);
    }

    private void PrintPawnsThatCanCapture() {
        System.out.println("Pionki ktore moga bic koloru: " + MOVE);
        PossibleCapturesForAllPawns = CheckCaptures(MOVE);
        if(PossibleCapturesForAllPawns.size() == 0)
        {
            System.out.println("Brak opcji bicia");
        }
        else
        {
            for (Pawn possibleCapturesForAllPawn : PossibleCapturesForAllPawns) {
                System.out.println(possibleCapturesForAllPawn.getX() / FIELD_SIZE + " | " + possibleCapturesForAllPawn.getY() / FIELD_SIZE);
            }
            System.out.println("===============================================");
        }
    }

    private Pawn[][] CloneArray(Pawn [][] PawnsToClone) {
        Pawn[][] pawns = new Pawn[ROWS][COLUMNS];

        for (int i = 0; i < COLUMNS; i++)
        {
            System.arraycopy(PawnsToClone[i], 0, pawns[i], 0, COLUMNS);
        }

        return pawns;
    }

    private double ReturnDistanceBetweenPoints(Point first, Point second) {
        int x1 = first.getX();
        int y1 = first.getY();
        int x2 = second.getX();
        int y2 = second.getY();
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / FIELD_SIZE;
        int y = e.getY() / FIELD_SIZE;
        PossibleCapturesForSpecificPawn.clear();

        Pawn[][] CopyOfPawns = CloneArray(PAWN);
        ReturnCapturesOfSpecificPawn(PAWN[y][x], new Point(x, y), CopyOfPawns);
        System.out.println("Mozliwe bicia dla " + PAWN[y][x].getColor() + " | " + x + " | " + y);
        for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++)
        {
            CapturePathClass Path = AllOptionsOfCapturingForSpecificPawn.get(i);
            Vector<Point> vector = Path.getPath();
            if(!Path.isFinished())
            {
                continue;
            }
            for (int j = 0; j < vector.size(); j++)
            {
                Point point = vector.get(j);
                System.out.println(point.getX() + " | " + point.getY());
            }
            System.out.println("Lub");
        }
        AllOptionsOfCapturingForSpecificPawn.clear();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX()/FIELD_SIZE;
        int y = e.getY()/FIELD_SIZE;
        if(PAWN[y][x] != null)
        {
            System.out.println("Zlapalem piona: " + PAWN[y][x].getColor() + " " + x + " | " + y);
            SELECTED_PAWN_Y = y;
            SELECTED_PAWN_X = x;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX()/FIELD_SIZE;
        int y = e.getY()/FIELD_SIZE;

        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(x * FIELD_SIZE);
        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(y * FIELD_SIZE);

        if(ValidateMove(x, y, PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X]))
        {
            PAWN[y][x] = PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X];
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X] = null;
            ChangeMove();
            PrintPawnsThatCanCapture();
        }
        else
        {
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(SELECTED_PAWN_X * FIELD_SIZE);
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(SELECTED_PAWN_Y * FIELD_SIZE);
        }
        repaint();
        PossibleCapturesForAllPawns.clear();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(e.getX() - FIELD_SIZE / 2);
        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(e.getY() - FIELD_SIZE / 2);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
