import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JPanel{
    private Image image;
    private ImageIcon but1;
    private ImageIcon but2;
    private int width;
    private int height;

    public MyButton(int width,int height,String name1,String name2){
        this.width = width;
        this.height = height;
        but1 = new ImageIcon(name1);
        but2 = new ImageIcon(name2);
        setSize(width,height);
        image = but2.getImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                image = but1.getImage();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                image = but2.getImage();
                repaint();
            }
        });
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image,0,0,width,height,this);
    }
}
