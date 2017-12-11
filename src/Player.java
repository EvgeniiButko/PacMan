import javax.swing.*;
import java.awt.*;

public class Player extends Rectangle{
    private int speed = 2;

    public Player(int x,int y, int size){
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
