import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

public class Menu extends JFrame{
    private GamePanel w = null;
    private MapMaker m;
    private Menu thisMenu = this;
    private MenuPanel menu = new MenuPanel();
    private MenuPanel2 menu2 = new MenuPanel2();
    private MenuPanel3 menu3 = new MenuPanel3();
    private PausePanel menu4 = new PausePanel();
    private int count;
    private int MaxCount = 1;
    private boolean flag;
    private String fileName1 = "Map1.txt";
    private String fileName2 = "lvl1.txt";
    private String fileName3 = "lvl2.txt";
    private HashMap<Integer,String> lvls = new HashMap<>();
    private int Result = 0;
    private int life = 3;
    int CountForMenu4 = 0;
    public int getLife(){
        return life;
    }
    public void setLife(int life){
        this.life = life;
    }

    public int getResult(){
        return Result;
    }
    public void setResult(int result){
        Result = result;
    }

    Menu(GamePanel a) {
        setResizable(false);
        w = a;
        AddNewMap(fileName2);
        AddNewMap(fileName3);
        //--------------------------------------MainMenu---------------------------------------//
        menu.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remove(menu);
                w = new GamePanel("Map1.txt",thisMenu);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException ex){}
                add(w);
                Result = 0;
                life = 3;
                setSize(416,380);
                repaint();
            }
        });

        menu.button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remove(menu);
                w = new GamePanel("MAP2.txt",thisMenu);
                flag = true;
                add(w);
                life = 3;
                Result = 0;
                setSize(416,380);
                repaint();
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
                if(!flag){
                    w = new GamePanel("Map1.txt",thisMenu);
                    life = 3;
                }else{
                    w = new GamePanel("MAP2.png",thisMenu);
                    life = 3;
                    flag = false;
                }
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

                if(count == MaxCount){
                    w = new GamePanel(fileName1,thisMenu);
                    count = 0;
                }else{
                    w = new GamePanel(lvls.get(count),thisMenu);
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

        menu4.button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               try {
                   remove(w);
                   remove(menu4);
                   add(menu);
                   w.Stop();
                   w = null;
                   setSize(400, 400);
               }catch (NullPointerException ex){}
               setSize(399,399);
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
                    synchronized (w.gameLogic.x) {
                        w.gameLogic.x.notifyAll();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT)w.gameLogic.way = 1;
                    if (e.getKeyCode() == KeyEvent.VK_UP) w.gameLogic.way = 3;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) w.gameLogic.way = 2;
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) w.gameLogic.way = 4;
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if(CountForMenu4 == 0) {
                            w.Pause();
                            w.gameLogic.Pause();
                            CountForMenu4++;
                            remove(w);
                            add(menu4);
                            setSize(400,400);
                        }else{
                            CountForMenu4 = 0;
                            remove(menu4);
                            add(w);
                            w.Start();
                            w.gameLogic.Start();
                            setSize(416,380);
                        }
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
        try{
            remove(w);
        }catch (NullPointerException e){}
        w = null;
        add(menu3);
        setSize(400,400);
    }

    public void AddNewMap(String fileName){
        if(new File(fileName).exists()) {
            lvls.put(MaxCount, fileName);
            MaxCount++;
        }
    }
}
