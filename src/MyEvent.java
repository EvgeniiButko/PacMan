import java.awt.Window;
import java.awt.event.WindowEvent;

public class MyEvent extends WindowEvent {
    public MyEvent(Window source) {
        super(source, 1000);
    }
}
