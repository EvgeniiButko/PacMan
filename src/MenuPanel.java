import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private MyButton button = new MyButton(100,50,180,45,"ButON.png","ButOFF.png");
    private MyButton button1 = new MyButton(100,150,180,45,"ButON.png","ButOFF.png");
    private Image img = new ImageIcon("FUCK.png").getImage();

    public MenuPanel(){
       setSize(400,400);
       add(button);
       add(button1);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,400,400,this);
        button.setBounds(20,20,180,45);
        button1.setBounds(20,100,180,45);

    }
}
