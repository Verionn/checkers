import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import static java.lang.Math.abs;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private static final int FIELD_SIZE = 80;
    private static final int PAWN_SIZE = 60;
    private static final int PAWN_OFFSET = 10;
    private static final int SMALL_PAWN_SIZE = 20;
    private static final int SMALL_PAWN_OFFSET = 30;
    private static final int QUEEN_SIZE = 30;
    private static final int QUEEN_OFFSET = 25;
    private static final int ROWS = 8;
    private static int WHITE_PAWNS = 12;
    private static int RED_PAWNS = 12;
    private static final int COLUMNS = 8;
    private final String QUEEN_TYPE = "Queen";
    private final String PAWN_TYPE = "Pawn";
    private final double DIFF_BETWEEN_FIELDS = ReturnDistanceBetweenPoints(new Point(2, 2), new Point(4, 4));
    private int SELECTED_PAWN_X;
    private int SELECTED_PAWN_Y;
    private final Pawn[][] PAWN = new Pawn[ROWS][COLUMNS];
    private final Vector<Pawn> PawnsWhichCanCapture = new Vector<>();
    private final Vector<CapturePath> AllOptionsOfCapturingForSpecificPawn = new Vector<>();
    private final Vector<CapturePath> MandatoryMoves = new Vector<>();

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
                if (PAWN[i][j] != null)
                {
                    if(!PAWN[i][j].isQueen()) {
                        if (PAWN[i][j].getColor().equals("RED")) {
                            g2d.setColor(Color.RED);
                            g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                        } else {
                            g2d.setColor(Color.WHITE);
                            g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                        }
                    }
                    else
                    {
                        if (PAWN[i][j].getColor().equals("RED")) {
                            g2d.setColor(Color.RED);
                            g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            g2d.setColor(Color.WHITE);
                            g2d.fillOval(PAWN[i][j].getX() + QUEEN_OFFSET , PAWN[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                            g2d.setColor(Color.RED);
                            g2d.fillOval(PAWN[i][j].getX() + SMALL_PAWN_OFFSET , PAWN[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);
                        } else {
                            g2d.setColor(Color.WHITE);
                            g2d.fillOval(PAWN[i][j].getX() + PAWN_OFFSET, PAWN[i][j].getY() + PAWN_OFFSET, PAWN_SIZE, PAWN_SIZE);
                            g2d.setColor(Color.RED);
                            g2d.fillOval(PAWN[i][j].getX() + QUEEN_OFFSET , PAWN[i][j].getY() + QUEEN_OFFSET, QUEEN_SIZE, QUEEN_SIZE);
                            g2d.setColor(Color.WHITE);
                            g2d.fillOval(PAWN[i][j].getX() + SMALL_PAWN_OFFSET , PAWN[i][j].getY() + SMALL_PAWN_OFFSET, SMALL_PAWN_SIZE, SMALL_PAWN_SIZE);

                        }
                    }
                }
            }
        }
    }

    private void AddPieces() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i < 3 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("RED", j * FIELD_SIZE, i * FIELD_SIZE, false);
                }
                if (i > 4 && (i + j) % 2 == 1) {
                    PAWN[i][j] = new Pawn("WHITE", j * FIELD_SIZE, i * FIELD_SIZE, false);
                }
            }
        }
    }

    private void UpdateMandatoryMoves() {
        CapturePath Path = MandatoryMoves.lastElement();
        MandatoryMoves.clear();
        MandatoryMoves.add(Path);
    }

    private void SubtractPiece() {
        if(BoardFrame.MOVE.equals("RED")) {
            WHITE_PAWNS--;
        }
        else{
            RED_PAWNS--;
        }

        System.out.println("WHITE: " + WHITE_PAWNS + " | RED: " + RED_PAWNS);
    }

    private boolean CheckStartingPoint(int x, int y) {
        for (CapturePath Path : MandatoryMoves) {
            if (Path.getStartingPoint().getX() == x && Path.getStartingPoint().getY() == y) {
                return true;
            }
        }

        return false;
    }

    private Point ReturnMePositionOfCapturedPawn(Point first, Point second) {
        int x1 = first.getX();
        int y1 = first.getY();

        int x2 = second.getX();
        int y2 = second.getY();

        int AmountOfFieldsBetween = abs(x1-x2);

        if(x1 > x2) {
            if(y1 > y2){
                for (int i = 0; i < AmountOfFieldsBetween; i++) {
                    if(PAWN[y1-i][x1-i] != null) {
                        if(!PAWN[y1-i][x1-i].getColor().equals(BoardFrame.MOVE)) {
                            return new Point(x1-i, y1-i);
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < AmountOfFieldsBetween; i++) {
                    if(PAWN[y1+i][x1-i] != null) {
                        if(!PAWN[y1+i][x1-i].getColor().equals(BoardFrame.MOVE)) {
                            return new Point(x1-i, y1+i);
                        }
                    }
                }
            }
        }
        else {
            if(y1 > y2) {
                for (int i = 0; i < AmountOfFieldsBetween; i++) {
                    if(PAWN[y1-i][x1+i] != null) {
                        if(!PAWN[y1-i][x1+i].getColor().equals(BoardFrame.MOVE)) {
                            return new Point(x1+i, y1-i);
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < AmountOfFieldsBetween; i++) {
                    if(PAWN[y1+i][x1+i] != null) {
                        if(!PAWN[y1+i][x1+i].getColor().equals(BoardFrame.MOVE)) {
                            return new Point(x1+i, y1+i);
                        }
                    }
                }
            }
        }
        return first;
    }
    private boolean ValidateMove(int x, int y, Pawn pawn) {
        if (!BoardFrame.MOVE.equals(pawn.getColor())) {
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
        if(MandatoryMoves.size() != 0) {
            for (int i = 0; i < MandatoryMoves.size(); i++) {
                CapturePath Path = MandatoryMoves.get(i);
                Vector<Point> CapturePath = Path.getPath();
                if(!CheckStartingPoint(SELECTED_PAWN_X, SELECTED_PAWN_Y))
                {
                    System.out.println("Bicie zlym pionkiem");
                    return false;
                }
                System.out.println("Starting point: " + Path.getStartingPoint().getX() + " | " + Path.getStartingPoint().getY());
                for (int j = 0; j < CapturePath.size(); j++)
                {
                    Point point = CapturePath.get(j);
                    if(point.getX() == x && point.getY() == y)
                    {
                        Path.setStartingPoint(CapturePath.firstElement());
                        CapturePath.remove(j);
                        Path.setPath(CapturePath);
                        MandatoryMoves.remove(i);
                        MandatoryMoves.add(Path);

                        Point CapturedPawnPos = ReturnMePositionOfCapturedPawn(new Point(SELECTED_PAWN_X, SELECTED_PAWN_Y), point);
                        PAWN[CapturedPawnPos.getY()][CapturedPawnPos.getX()] = null;
                        SubtractPiece();

                        if(MandatoryMoves.size() > 1) {
                            UpdateMandatoryMoves();
                        }

                        return true;
                    }
                }
            }
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

    private void CheckCaptures(String color) {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (PAWN[i][j] != null) {
                    if (PAWN[i][j].getColor().equals(color))
                    {
                        if (CheckIfPieceCanCapture(PAWN[i][j], PAWN))
                        {
                            PawnsWhichCanCapture.add(PAWN[i][j]);
                        }
                    }
                }
            }
        }
    }

    private Point CheckLeftBottomCornerAsPawn(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        if (x - 1 >= 0 && y + 1 <= 7 && Pawn[y + 1][x - 1] != null) {
            if (!Pawn[y + 1][x - 1].getColor().equals(pawn.getColor())) {
                if (x - 2 >= 0 && y + 2 <= 7 && Pawn[y + 2][x - 2] == null) {
                    return new Point(x - 2, y + 2);
                }
            }
        }
        return null;
    }

    private Point CheckRightBottomCornerAsPawn(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        if (x + 1 <= 7 && y + 1 <= 7 && Pawn[y + 1][x + 1] != null) {
            if (!Pawn[y + 1][x + 1].getColor().equals(pawn.getColor())) {
                if (x + 2 <= 7 && y + 2 <= 7 && Pawn[y + 2][x + 2] == null) {
                    return new Point(x + 2, y + 2);
                }
            }
        }
        return null;
    }

    private Point CheckLeftTopCornerAsPawn(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        if (y - 1 >= 0 && x - 1 >= 0 && Pawn[y - 1][x - 1] != null) {
            if (!Pawn[y - 1][x - 1].getColor().equals(pawn.getColor())) {
                if (x - 2 >= 0 && y - 2 >= 0 && Pawn[y - 2][x - 2] == null) {
                    return new Point(x - 2, y - 2);
                }
            }
        }
        return null;
    }

    private Point CheckRightTopCornerAsPawn(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        if (x + 1 <= 7 && y - 1 >= 0 && Pawn[y - 1][x + 1] != null) {
            if (!Pawn[y - 1][x + 1].getColor().equals(pawn.getColor())) {
                if (x + 2 <= 7 && y - 2 >= 0 && Pawn[y - 2][x + 2] == null) {
                    return new Point(x + 2, y - 2);
                }
            }
        }
        return null;
    }

    private Point CheckRightTopCornerAsQueen(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        for(int i = 1; i < 7; i++) {
            if (y - i >= 0 && x + i <= 7) {
                if (Pawn[y - i][x + i] != null && !Pawn[y - i][x + i].getColor().equals(pawn.getColor())) {
                    if (x + i + 1 <= 7 && y - i - 1 >= 0 && Pawn[y - i-1][x + i + 1] == null) {
                        return new Point(x + i + 1, y - i - 1);
                    }
                }
            }
        }
        return null;
    }

    private Point CheckLeftTopCornerAsQueen(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        for(int i = 1; i < 7; i++) {
            if (y - i >= 0 && x - i >= 0) {
                if (Pawn[y - i][x - i] != null && !Pawn[y - i][x - i].getColor().equals(pawn.getColor())) {
                    if (x - i - 1 >= 0 && y - i - 1 >= 0 && Pawn[y - i - 1][x - i - 1] == null) {
                        return new Point(x - i - 1, y - i - 1);
                    }
                }
            }
        }
        return null;
    }

    private Point CheckLeftBottomCornerAsQueen(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        for(int i = 1; i < 7; i++) {
            if (y + i <= 7 && x - i >= 0) {
                if (Pawn[y + i][x - i] != null && !Pawn[y + i][x - i].getColor().equals(pawn.getColor())) {
                    if (x - i - 1 >= 0 && y + i + 1 <= 7 && Pawn[y + i + 1][x - i - 1] == null) {
                        return new Point(x - i - 1, y + i + 1);
                    }
                }
            }
        }
        return null;
    }

    private Point CheckRightBottomCornerAsQueen(Pawn pawn, int x, int y, Pawn[][] Pawn) {
        for(int i = 1; i < 7; i++) {
            if (y + i <= 7 && x + i <= 7) {
                if (Pawn[y + i][x + i] != null && !Pawn[y + i][x + i].getColor().equals(pawn.getColor())) {
                    if (x + i + 1 <= 7 && y + i + 1 <= 7 && Pawn[y + i + 1][x + i + 1] == null) {
                        return new Point(x + i + 1, y + i + 1);
                    }
                }
            }
        }
        return null;
    }

    private boolean CheckIfFieldIsInDiagonal(Point first, Point second) {
        int x1 = first.getX();
        int y1 = first.getY();

        int x2 = second.getX();
        int y2 = second.getY();

        for(int i = 0; i < 8; i++) {
            if(x1+i > 7 || y1 + i > 7) {
                break;
            }
            if(x1+i == x2 && y1 + i == y2) {
                return true;
            }
        }

        for(int i = 0; i < 8; i++) {
            if(x1-i < 0 || y1 - i < 0) {
                break;
            }
            if(x1 - i == x2 && y1 - i == y2) {
                return true;
            }
        }

        for(int i = 0; i < 8; i++) {
            if(x1+i > 7 || y1 - i < 0) {
                break;
            }
            if(x1 + i == x2 && y1 - i == y2) {
                return true;
            }
        }

        for(int i = 0; i < 8; i++) {
            if(x1-i < 0 || y1 + i > 7) {
                break;
            }
            if(x1 - i == x2 && y1 + i == y2) {
                return true;
            }
        }
        return false;
    }

    private boolean ValidateCapture(Point first, Point second) {
        int x1 = first.getX();
        int y1 = first.getY();
        int x2 = second.getX();
        int y2 = second.getY();

        return PAWN[(y1 + y2) / 2][(x1 + x2) / 2] != null;
    }

    private VectorInfo ReturnVectorWithSpecficLastPoint(Point CheckedPoint, String type) {
        for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++) {
            CapturePath Path = AllOptionsOfCapturingForSpecificPawn.get(i);
            if (Path.isFinished()) {
                continue;
            }
            Point LastPoint = Path.getPath().lastElement();
            if(type.equals(PAWN_TYPE)) {
                if (ReturnDistanceBetweenPoints(LastPoint, CheckedPoint) == DIFF_BETWEEN_FIELDS) {
                    return new VectorInfo(i, -1);
                }
            }
            else if(type.equals(QUEEN_TYPE)){
                if(CheckIfFieldIsInDiagonal(LastPoint, CheckedPoint)) {
                    return new VectorInfo(i, -1);
                }
            }
        }
        for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++) {
            CapturePath Path = AllOptionsOfCapturingForSpecificPawn.get(i);
            Vector<Point> vector = Path.getPath();
            for (int j = 0; j < vector.size() - 1; j++) {
                Point point = vector.get(j);
                if (ReturnDistanceBetweenPoints(point, CheckedPoint) == DIFF_BETWEEN_FIELDS) {
                    if (ValidateCapture(point, CheckedPoint)) {
                        return new VectorInfo(i, j);
                    }
                }
            }
        }
        return new VectorInfo(-1, -1);
    }

    private void CheckIfCaptureCreateNewPath(Point CheckedPoint, String type, Point FirstPoint) {
        if (AllOptionsOfCapturingForSpecificPawn.size() == 0) {
            Vector<Point> FirstPath = new Vector<>();
            FirstPath.add(CheckedPoint);
            CapturePath Path = new CapturePath(FirstPath, false, FirstPoint);
            AllOptionsOfCapturingForSpecificPawn.add(Path);
            return;
        }

        VectorInfo PathIndex = ReturnVectorWithSpecficLastPoint(CheckedPoint, type);
        if (PathIndex.getI() == -1 && PathIndex.getJ() == -1) {
            Vector<Point> NewPath = new Vector<>();
            NewPath.add(CheckedPoint);
            CapturePath Path = new CapturePath(NewPath, false,FirstPoint);
            AllOptionsOfCapturingForSpecificPawn.add(Path);
        }
        else if (PathIndex.getI() >= 0 && PathIndex.getJ() == -1) {
            CapturePath Path = AllOptionsOfCapturingForSpecificPawn.get(PathIndex.getI());
            Vector<Point> FoundPath = Path.getPath();
            FoundPath.add(CheckedPoint);
            AllOptionsOfCapturingForSpecificPawn.remove(PathIndex.getI());
            AllOptionsOfCapturingForSpecificPawn.add(Path);
        }
        else if (PathIndex.getI() >= 0 && PathIndex.getJ() >= 0) {
            CapturePath Path = AllOptionsOfCapturingForSpecificPawn.get(PathIndex.getI());
            Vector<Point> FoundPath = Path.getPath();
            Vector<Point> NewPath = new Vector<>();
            for (int i = 0; i <= PathIndex.getJ(); i++) {
                NewPath.add(FoundPath.get(i));
            }
            NewPath.add(CheckedPoint);
            CapturePath NewPathClass = new CapturePath(NewPath, false, Path.getStartingPoint());
            AllOptionsOfCapturingForSpecificPawn.add(NewPathClass);
        }
    }

    private Vector<Point> ReturnMovesAfterCaptureOfQueen(Pawn pawn, int x, int y, Pawn[][] Pawn, String direction) {
        Vector<Point> PossibleMoves = new Vector<>();
        Vector<Point> Moves = new Vector<>();

        label:
        for(int i = 0; i < 7; i++) {
            switch (direction) {
                case "LeftTop":
                    if (y - i >= 0 && x - i >= 0) {
                        if (Pawn[y - i][x - i] == null) {
                            Moves.add(new Point(x - i, y - i));
                        } else {
                            break label;
                        }
                    }
                    break;
                case "RightTop":
                    if (y - i >= 0 && x + i <= 7) {
                        if (Pawn[y - i][x + i] == null) {
                            Moves.add(new Point(x + i, y - i));
                        } else {
                            break label;
                        }
                    }
                    break;
                case "LeftBottom":
                    if (y + i <= 7 && x - i >= 0) {
                        if (Pawn[y + i][x - i] == null) {
                            Moves.add(new Point(x - i, y + i));
                        } else {
                            break label;
                        }
                    }
                    break;
                default:
                    if (y + i <= 7 && x + i <= 7) {
                        if (Pawn[y + i][x + i] == null) {
                            Moves.add(new Point(x + i, y + i));
                        } else {
                            break label;
                        }
                    }
                    break;
            }
        }

        if(Moves.size() != 0) {
            for (Point point : Moves) {
                if (CheckIfPieceCanCapture(new Pawn(pawn.getColor(), point.getX() * FIELD_SIZE, point.getY() * FIELD_SIZE, true), Pawn)) {
                    PossibleMoves.add(point);
                }
            }
        }
        if(PossibleMoves.size() != 0) {
            return PossibleMoves;
        }
        else {
            return Moves;
        }
    }

    private void ReturnCapturesOfSpecificPawn(Pawn pawn, Point prevField, Pawn[][] CopyOfPawns) {

        int x = pawn.getX() / FIELD_SIZE;
        int y = pawn.getY() / FIELD_SIZE;

        if(pawn.isQueen()) {
            Point leftTopCorner = CheckLeftTopCornerAsQueen(pawn, x, y, CopyOfPawns);
            if (leftTopCorner != null) {
                if (prevField.getX() != x - leftTopCorner.getX() || prevField.getY() != y - leftTopCorner.getY()) {

                    Pawn[][] CopyOfBoard = CloneArray(CopyOfPawns);
                    CopyOfBoard[leftTopCorner.getY() + 1][leftTopCorner.getX() + 1] = null;
                    Vector<Point> PossiblePlacesForQueen = ReturnMovesAfterCaptureOfQueen(pawn, leftTopCorner.getX(), leftTopCorner.getY(), CopyOfBoard, "LeftTop");

                    for (Point point : PossiblePlacesForQueen) {
                        Pawn[][] LastCopyOfPawns = CloneArray(CopyOfBoard);

                        CheckIfCaptureCreateNewPath(point, QUEEN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                        LastCopyOfPawns[point.getY()][point.getX()] = LastCopyOfPawns[y][x];
                        LastCopyOfPawns[y][x] = null;
                        ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), point.getX() * FIELD_SIZE, point.getY() * FIELD_SIZE, true), new Point(x, y), LastCopyOfPawns);
                    }
                }
            }

            Point rightTopCorner = CheckRightTopCornerAsQueen(pawn, x, y, CopyOfPawns);
            if (rightTopCorner != null) {
                if (prevField.getX() != x + rightTopCorner.getX() || prevField.getY() != y - rightTopCorner.getY()) {

                    Pawn[][] CopyOfBoard = CloneArray(CopyOfPawns);
                    CopyOfBoard[rightTopCorner.getY() + 1][rightTopCorner.getX() - 1] = null;
                    Vector<Point> PossiblePlacesForQueen = ReturnMovesAfterCaptureOfQueen(pawn, rightTopCorner.getX(), rightTopCorner.getY(), CopyOfBoard, "RightTop");

                    for (Point point : PossiblePlacesForQueen) {
                        Pawn[][] LastCopyOfPawns = CloneArray(CopyOfBoard);

                        CheckIfCaptureCreateNewPath(point, QUEEN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                        LastCopyOfPawns[point.getY()][point.getX()] = LastCopyOfPawns[y][x];
                        LastCopyOfPawns[y][x] = null;
                        ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), point.getX() * FIELD_SIZE, point.getY() * FIELD_SIZE, true), new Point(x, y), LastCopyOfPawns);
                    }
                }
            }

            Point leftBottomCorner = CheckLeftBottomCornerAsQueen(pawn, x, y, CopyOfPawns);
            if (leftBottomCorner != null) {
                if (prevField.getX() != x - leftBottomCorner.getX() || prevField.getY() != y + leftBottomCorner.getY()) {

                    Pawn[][] CopyOfBoard = CloneArray(CopyOfPawns);
                    CopyOfBoard[leftBottomCorner.getY() - 1][leftBottomCorner.getX() + 1] = null;
                    Vector<Point> PossiblePlacesForQueen = ReturnMovesAfterCaptureOfQueen(pawn, leftBottomCorner.getX(), leftBottomCorner.getY(), CopyOfBoard, "LeftBottom");

                    for (Point point : PossiblePlacesForQueen) {
                        Pawn[][] LastCopyOfPawns = CloneArray(CopyOfBoard);

                        CheckIfCaptureCreateNewPath(point, QUEEN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                        LastCopyOfPawns[point.getY()][point.getX()] = LastCopyOfPawns[y][x];
                        LastCopyOfPawns[y][x] = null;
                        ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), point.getX() * FIELD_SIZE, point.getY() * FIELD_SIZE, true), new Point(x, y), LastCopyOfPawns);
                    }
                }
            }

            Point rightBottomCorner = CheckRightBottomCornerAsQueen(pawn, x, y, CopyOfPawns);
            if (rightBottomCorner != null) {
                if (prevField.getX() != x + rightBottomCorner.getX() || prevField.getY() != y + rightBottomCorner.getY()) {

                    Pawn[][] CopyOfBoard = CloneArray(CopyOfPawns);
                    CopyOfBoard[rightBottomCorner.getY() - 1][rightBottomCorner.getX() - 1] = null;
                    Vector<Point> PossiblePlacesForQueen = ReturnMovesAfterCaptureOfQueen(pawn, rightBottomCorner.getX(), rightBottomCorner.getY(), CopyOfBoard, "RightBottom");

                    for (Point point : PossiblePlacesForQueen) {
                        Pawn[][] LastCopyOfPawns = CloneArray(CopyOfBoard);

                        CheckIfCaptureCreateNewPath(point, QUEEN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                        LastCopyOfPawns[point.getY()][point.getX()] = LastCopyOfPawns[y][x];
                        LastCopyOfPawns[y][x] = null;
                        ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), point.getX() * FIELD_SIZE, point.getY() * FIELD_SIZE, true), new Point(x, y), LastCopyOfPawns);
                    }

                }
            }
        }
        else
        {
            Point leftTopCorner = CheckLeftTopCornerAsPawn(pawn, x, y, CopyOfPawns);
            if (leftTopCorner != null) {
                if (prevField.getX() != x - 2 || prevField.getY() != y - 2) {

                    Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                    CheckIfCaptureCreateNewPath(leftTopCorner, PAWN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                    LastCopyOfPawns[y - 1][x - 1] = null;
                    LastCopyOfPawns[y - 2][x - 2] = LastCopyOfPawns[y][x];
                    LastCopyOfPawns[y][x] = null;

                    ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), x * FIELD_SIZE - 2 * FIELD_SIZE, y * FIELD_SIZE - 2 * FIELD_SIZE, false), new Point(x, y), LastCopyOfPawns);
                }
            }

            Point rightTopCorner = CheckRightTopCornerAsPawn(pawn, x, y, CopyOfPawns);
            if (rightTopCorner != null) {
                if (prevField.getX() != x + 2 || prevField.getY() != y - 2) {

                    Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                    CheckIfCaptureCreateNewPath(rightTopCorner, PAWN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                    LastCopyOfPawns[y - 1][x + 1] = null;
                    LastCopyOfPawns[y - 2][x + 2] = LastCopyOfPawns[y][x];
                    LastCopyOfPawns[y][x] = null;

                    ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), x * FIELD_SIZE + 2 * FIELD_SIZE, y * FIELD_SIZE - 2 * FIELD_SIZE, false), new Point(x, y), LastCopyOfPawns);
                }
            }

            Point leftBottomCorner = CheckLeftBottomCornerAsPawn(pawn, x, y, CopyOfPawns);
            if (leftBottomCorner != null) {
                if (prevField.getX() != x - 2 || prevField.getY() != y + 2) {

                    Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                    CheckIfCaptureCreateNewPath(leftBottomCorner, PAWN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                    LastCopyOfPawns[y + 1][x - 1] = null;
                    LastCopyOfPawns[y + 2][x - 2] = LastCopyOfPawns[y][x];
                    LastCopyOfPawns[y][x] = null;

                    ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), x * FIELD_SIZE - 2 * FIELD_SIZE, y * FIELD_SIZE + 2 * FIELD_SIZE, false), new Point(x, y), LastCopyOfPawns);
                }
            }

            Point rightBottomCorner = CheckRightBottomCornerAsPawn(pawn, x, y, CopyOfPawns);
            if (rightBottomCorner != null) {
                if (prevField.getX() != x + 2 || prevField.getY() != y + 2) {

                    Pawn[][] LastCopyOfPawns = CloneArray(CopyOfPawns);
                    CheckIfCaptureCreateNewPath(rightBottomCorner, PAWN_TYPE, new Point(pawn.getX()/FIELD_SIZE, pawn.getY()/FIELD_SIZE));

                    LastCopyOfPawns[y + 1][x + 1] = null;
                    LastCopyOfPawns[y + 2][x + 2] = LastCopyOfPawns[y][x];
                    LastCopyOfPawns[y][x] = null;

                    ReturnCapturesOfSpecificPawn(new Pawn(pawn.getColor(), x * FIELD_SIZE + 2 * FIELD_SIZE, y * FIELD_SIZE + 2 * FIELD_SIZE, false), new Point(x, y), LastCopyOfPawns);
                }
            }
        }

        if (!CheckIfPieceCanCapture(pawn, CopyOfPawns)) {
            for (int i = 0; i < AllOptionsOfCapturingForSpecificPawn.size(); i++) {
                CapturePath Path = AllOptionsOfCapturingForSpecificPawn.get(i);
                Vector<Point> CapturePath = Path.getPath();
                Point LastPoint = CapturePath.lastElement();
                if (LastPoint.getY() == y && LastPoint.getX() == x) {
                    Path.setFinished(true);
                    AllOptionsOfCapturingForSpecificPawn.remove(i);
                    AllOptionsOfCapturingForSpecificPawn.add(Path);
                }
            }
        }
    }

    private boolean CheckIfPieceCanCapture(Pawn pawn, Pawn[][] CopyOfPawns) {
        int y = pawn.getY() / FIELD_SIZE;
        int x = pawn.getX() / FIELD_SIZE;

        if (pawn.isQueen()) {
            System.out.println("Jestem damka");
            if (CheckLeftTopCornerAsQueen(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if (CheckRightTopCornerAsQueen(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if (CheckLeftBottomCornerAsQueen(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            return CheckRightBottomCornerAsQueen(pawn, x, y, CopyOfPawns) != null;
        }
        else {
            if (CheckLeftTopCornerAsPawn(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if (CheckRightTopCornerAsPawn(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            if (CheckLeftBottomCornerAsPawn(pawn, x, y, CopyOfPawns) != null) {
                return true;
            }
            return CheckRightBottomCornerAsPawn(pawn, x, y, CopyOfPawns) != null;
        }
    }

    private void ChangeMove() {

        if(MandatoryMoves.size() != 0  &&  MandatoryMoves.get(0).getPath().size() != 0)
        {
            System.out.println("RUCH: " + BoardFrame.MOVE);
            return;
        }
        if (BoardFrame.MOVE.equals("WHITE")) {
            BoardFrame.MOVE = "RED";
        }
        else {
            BoardFrame.MOVE = "WHITE";
        }
        MandatoryMoves.clear();
        System.out.println("RUCH: " + BoardFrame.MOVE);
    }

    private Pawn[][] CloneArray(Pawn[][] PawnsToClone) {
        Pawn[][] pawns = new Pawn[ROWS][COLUMNS];

        for (int i = 0; i < COLUMNS; i++) {
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

    private int ReturnSizeOfTheLongestCapturePath() {
        int max = 0;
        for (CapturePath Path : AllOptionsOfCapturingForSpecificPawn) {
            if (Path.getPath().size() > max) {
                max = Path.getPath().size();
            }
        }
        return max;
    }

    private void CheckIfTheGameHasBeenEnded()
    {
        if(BoardFrame.GAME) {
            if (WHITE_PAWNS == 0) {
                BoardFrame.WINNER = "RED";
                BoardFrame.GAME = false;
                System.out.println("KONIEC GRY ZWYCIESTWO BIALYCH");
            } else if (RED_PAWNS == 0) {
                BoardFrame.WINNER = "WHITE";
                BoardFrame.GAME = false;
                System.out.println("KONIEC GRY - ZWYCIESTWO CZERWONYCH");
            }
        }
        else
        {
            if (WHITE_PAWNS > RED_PAWNS) {
                BoardFrame.WINNER = "WHITE";
                System.out.println("KONIEC GRY ZWYCIESTWO BIALYCH");
            } else if (RED_PAWNS > WHITE_PAWNS){
                BoardFrame.WINNER = "RED";
                System.out.println("KONIEC GRY - ZWYCIESTWO CZERWONYCH");
            }
            else{
                BoardFrame.WINNER = "DRAW";
                System.out.println("KONIEC GRY - REMIS");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX()/FIELD_SIZE;
        int y = e.getY()/FIELD_SIZE;
        if(PAWN[y][x] != null)
        {
            System.out.println(x + " | " + y);
            SELECTED_PAWN_Y = y;
            SELECTED_PAWN_X = x;
        }
    }
    private void ReturnCapturePath() {

        Pawn[][] CopyOfPawns = CloneArray(PAWN);


        for (Pawn pawn : PawnsWhichCanCapture) {
            ReturnCapturesOfSpecificPawn(pawn, new Point(pawn.getX(), pawn.getY()), CopyOfPawns);
        }
        int Max = ReturnSizeOfTheLongestCapturePath();

        for (CapturePath Path : AllOptionsOfCapturingForSpecificPawn) {
            Vector<Point> Moves = Path.getPath();
            if (Moves.size() == Max) {
                MandatoryMoves.add(Path);
                for (Point pkt : Moves) {
                    System.out.println(pkt.getX() + " | " + pkt.getY());
                }
                System.out.println("===================================");
            }
        }
        AllOptionsOfCapturingForSpecificPawn.clear();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX()/FIELD_SIZE;
        int y = e.getY()/FIELD_SIZE;

        System.out.println(SELECTED_PAWN_X + " | " + SELECTED_PAWN_Y);
        if(SELECTED_PAWN_Y == 0 && SELECTED_PAWN_X == 0)
        {
            return;
        } // zrobic tu popraweczki wariacie

        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(x * FIELD_SIZE);
        PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(y * FIELD_SIZE);

        if(ValidateMove(x, y, PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X]))
        {
            PAWN[y][x] = PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X];
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X] = null;
            ChangeMove();
            CheckCaptures(BoardFrame.MOVE);
            if(PawnsWhichCanCapture.size() != 0) {
                ReturnCapturePath();
            }

        }
        else
        {
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(SELECTED_PAWN_X * FIELD_SIZE);
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(SELECTED_PAWN_Y * FIELD_SIZE);
        }
        if(PAWN[y][x] != null && MandatoryMoves.size() ==  0){
            PAWN[y][x].CheckUpgrade();
        }
        repaint();
        CheckIfTheGameHasBeenEnded();
        PawnsWhichCanCapture.clear();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X] != null) {
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setX(e.getX() - FIELD_SIZE / 2);
            PAWN[SELECTED_PAWN_Y][SELECTED_PAWN_X].setY(e.getY() - FIELD_SIZE / 2);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
