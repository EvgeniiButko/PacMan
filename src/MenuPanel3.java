import javax.swing.*;
import java.awt.*;

public class MenuPanel3 extends JPanel{
    public MyButton button = new MyButton(180,45,"NEXT.png","NEXT.png");
    public MyButton button1 = new MyButton(180,45,"MAINMENU.png","MAINMENU.png");
    private Image img = new ImageIcon("formenu.jpg").getImage();

    public MenuPanel3(){
        setSize(400,400);
        add(button);
        add(button1);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,400,400,this);
        button.setBounds(100,20,180,45);
        button1.setBounds(100,100,180,45);
    }
}
