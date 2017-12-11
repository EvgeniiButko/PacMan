import javax.swing.*;
import java.awt.*;

public class Player extends Rectangle{
    private int speed = 2;
    public Image image;

    public Player(int x,int y, int size,String name){
        image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(name))).getImage();
        this.x = x;
        this.y = y;
        width = size;
        height = size;
    }

    public void move(int i){
        if(i == 1)x+=speed;
        if(i == 2) x-=speed;
        if(i == 3)y-=speed;
        if(i == 4)y+=speed;
    }
}
