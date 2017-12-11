import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    public MyButton button1 = new MyButton(180,45,"MAINMENU.png","MAINMENU.png");
    private Image img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("formenu.jpg"))).getImage();

    public PausePanel(){
        setSize(200,200);
        add(button1);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,400,400,this);
        button1.setBounds(100,100,180,45);
    }
}
