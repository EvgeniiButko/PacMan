import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Start{
    private static Game window;

    public static void main(String[] args) {
        Menu m = new Menu(window);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setVisible(true);
        m.setSize(25*16+16, 18*16+16*5);
        m.setResizable(true);
        }
    }

