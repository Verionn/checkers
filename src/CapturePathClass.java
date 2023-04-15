import java.util.Vector;

public class CapturePathClass {
    private Vector<Point> Path = new Vector<>();
    private boolean finished;

    public CapturePathClass(Vector<Point> path, boolean finished) {
        Path = path;
        this.finished = finished;
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

    public boolean isFinished() {
        return finished;
    }
}
