import javax.swing.*;
import java.awt.event.*;

public class Menu extends JFrame{
    private Game w = null;
    private MapMaker m;
    private Menu thisMenu = this;
    private MenuPanel menu = new MenuPanel();
    private MenuPanel2 menu2 = new MenuPanel2();
    private MenuPanel3 menu3 = new MenuPanel3();
    public int count;
    private boolean flag;
    private String fileName1 = "Map1.txt";
    private String fileName2 = "lvl1.txt";
    private String fileName3 = "lvl2.txt";

    Menu(Game a) {
        w = a;
        //--------------------------------------MainMenu---------------------------------------//
        menu.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remove(menu);
                w = new Game("Map1.txt",thisMenu);
                add(w);
                setSize(416,380);
            }
        });

        menu.button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remove(menu);
                w = new Game("MAP2.txt",thisMenu);
                flag = true;
                add(w);
                setSize(416,380);
            }
        });

        menu.button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        menu.button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                m = new MapMaker();
            }
        });
        add(menu);
        repaint();
//----------------------------------------------UnderMenu---------------------------------------------//

        menu2.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!flag){w = new Game("Map1.txt",thisMenu);
                }else{w = new Game("MAP2.png",thisMenu);}
                remove(menu2);
                add(w);
                setSize(416,380);
            }
        });
        menu2.button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(menu2);
                add(menu);
                flag = false;
                setSize(400,380);
            }
        });

//----------------------------------------SecondUnderMenu----------------------------------------------//
        menu3.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(count == 1)w = new Game(fileName2,thisMenu);
                if(count == 2)w = new Game(fileName3,thisMenu);
                if(count == 3){
                    w = new Game(fileName1,thisMenu);
                    count = 0;
                }
                remove(menu3);
                add(w);
                setSize(416,380);
            }
        });
        menu3.button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(menu3);
                add(menu);
                setSize(400,380);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    synchronized (w.x) {
                        w.x.notifyAll();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT)w.way = 1;
                    if (e.getKeyCode() == KeyEvent.VK_UP) w.way = 3;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) w.way = 2;
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) w.way = 4;
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        w.Stop();
                    }
                }catch (NullPointerException ex){}
            }
        });
    }

    public void ChangeForSecondMenu(){
        remove(w);
        add(menu2);
        setSize(400,400);
    }

    public void ChangeForTherdMenu(){
        count++;
        remove(w);
        add(menu3);
        setSize(400,400);
    }

}
