import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Intelij extends Rectangle implements ActionListener{
    private Cell[][] cells;
    private int way = 0;
    private int speed = 2;
    private int millis = 35;
    private Timer timer = new Timer(millis,this);
    private volatile int XX;
    private volatile int XY;
    private int count;
    private boolean isGOToAim = false;
    public Image image;

    public Intelij(int xa, int ya, int size, Cell[][] c,String name){
        image = new ImageIcon(name).getImage();
        x = xa;
        y = ya;
        width = size;
        height = size;
        cells = c;
        if(cells[x/width + 1][y / width] != null)way = 1;
        if(cells[x/width - 1][y / width] != null)way = 2;
        if(cells[x/width][y / width - 1] != null)way = 3;
        if(cells[x/width][y / width + 1] != null)way = 4;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    public int getWay(int w){
        int variant;

        for (int i = 0; i < 16; i++) {

            variant = new Random().nextInt(4) + 1;
            if (cells[x/width + 1][y/width] != null && variant == 1 && w != 2) return 1;
            if (cells[x/width - 1][y/width] != null && variant == 2 && w != 1) return 2;
            if (cells[x/width][y/width + 1] != null && variant == 4 && w != 3) return 4;
            if (cells[x/width][y/width - 1] != null && variant == 3 && w != 4) return 3;

        }

        return 0;
    }

    public void Stop(){
        timer.stop();
    }

    private void Move(int i){
        if(i == 1)x+=speed;
        if(i == 2)x-=speed;
        if(i == 3)y-=speed;
        if(i == 4)y+=speed;
    }
    public void start(){
        timer.start();
    }

    public void setAimForII(int a,int b){
        XX = a;
        XY = b;
    }

    private synchronized int GoToAim(){
        if (XX == x && XX != 0) {
                    if (XY > y) {
                        for (int i = y/width; i < XY/width; i++)
                            if (cells[x/width][i] == null) return 0;
                        return 4;
                    }
                    if (XY < y) {
                        for (int i = XY/width; i < y/width; i++)
                            if (cells[x/width][i] == null) return 0;
                        return 3;
                    }
                }
        if (XY == y && XY != 0) {
                    if (XX > x) {
                        for (int i = x/width; i < XX/width; i++)
                            if (cells[i][y/width] == null) return 0;
                        return 1;
                    }
                    if (XX < x) {
                        for (int i = XX/width; i < x/width; i++)
                            if (cells[i][y/width] == null) return 0;
                        return 2;
                    }
                }
            return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(count < 8 && !isGOToAim){
            count++;
            Move(way);
        }else{
            count = 0;

            if(!isGOToAim && GoToAim() != 0){  //ЕСЛИ сейчас моб не бежит
                way = GoToAim();
                isGOToAim = true;
            }else way = getWay(way);
        }

        if(isGOToAim){
            if(x/16 != XX/16 && y/16 != XY/16) {
                Move(way);
            }else {
                isGOToAim = false;
                XX = 0;
                XY = 0;
            }

        }
    }
}

