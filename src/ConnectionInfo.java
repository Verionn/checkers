import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConnectionInfo {
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private final String color;

    public ConnectionInfo(ObjectInputStream input, ObjectOutputStream output, String color) {
        this.input = input;
        this.output = output;
        this.color = color;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public String getColor() {
        return color;
    }
}
