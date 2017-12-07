import javax.swing.*;
import java.awt.*;

public class MenuPanel2 extends JPanel {
    public MyButton button = new MyButton(180,45,"TRY1.png","TRY2.png");
    public MyButton button1 = new MyButton(180,45,"MAINMENU.png","MAINMENU.png");
    private Image img = new ImageIcon("formenu.jpg").getImage();

    public MenuPanel2(){
        setSize(400,400);
        add(button);
        add(button1);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,400,400,this);
        button.setBounds(100,20,180,45);
        button1.setBounds(100,100,180,45);
    }
}
