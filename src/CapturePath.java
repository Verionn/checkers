import java.util.Vector;

public class CapturePath {
    private Vector<Point> Path = new Vector<>();
    private Point StartingPoint;
    private boolean finished;

    public CapturePath(Vector<Point> path, boolean finished, Point point) {
        Path = path;
        this.finished = finished;
        StartingPoint = point;
    }

    public void setPath(Vector<Point> path) {
        Path = path;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Vector<Point> getPath() {
        return Path;
    }

    public void setStartingPoint(Point startingPoint) {
        StartingPoint = startingPoint;
    }

    public Point getStartingPoint() {
        return StartingPoint;
    }

    public boolean isFinished() {
        return finished;
    }
}
