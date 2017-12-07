import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MyButton button = new MyButton(180,45,"ButON.png","ButOFF.png");
    public MyButton button1 = new MyButton(180,45,"CREATE1.png","CREATE2.png");
    public MyButton button2 = new MyButton(180,45,"PLAYMY.png","PLAYMY.png");
    public MyButton button3 = new MyButton(180,45,"EXIT1.png","EXIT2.png");
    private Image img = new ImageIcon("formenu.jpg").getImage();

    public MenuPanel(){
       setSize(400,400);
       add(button);
       add(button1);
       add(button2);
       add(button3);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,400,400,this);
        button.setBounds(100,20,180,45);
        button1.setBounds(100,100,180,45);
        button2.setBounds(100,180,180,45);
        button3.setBounds(100,260,180,45);
    }
}
