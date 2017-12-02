import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Window implements ActionListener {
    public JFrame frame = new JFrame();
    public int CellSize = 16;
    public int LONGITUDE = CellSize * 25;
    public int HIGH = CellSize * 18;
    private File bestFile = new File("Best.txt");
    public Cell[][] list = new Cell[25][18];
    public Cell[][] empty = new Cell[25][18];
    Player player ;
    private boolean inGame = true;
    private boolean pause = false;
    private int way = 0;
    Object x = new Object();
    private Intelij intelij;
    private Intelij mob1;
    private Intelij mob2;
    private int count;
    private int BestResult = 0;
    private Image bomb = new ImageIcon("bomb.png").getImage();
    private Image stone = new ImageIcon("Камень.png").getImage();
    private Image simpleBlock = new ImageIcon("simpleblock.png").getImage();
    private boolean inVisability = false;
    private int inVisabilityCount = 0;
    Timer timer;
    private String lvl1 = "lvl1.txt";
    private String lvl2 = "lvl2.txt";
    private String lvl3 = "lvl3.txt";
    private File f = new File("E:/Lil Pump - Gucci Gang.wav");
    private Sound sound = new Sound(f);

    Thread MyLogic = new Thread(new Runnable() {
        @Override
        public void run() {
            while(inGame){
                       if(isCanMove(way) && !pause) {
                           int myway = way;
                               for (int i = 0; i < 8; i++) {
                                   player.move(myway);
                                   try {
                                       Thread.sleep(45);
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                               if(empty[player.x/CellSize][player.y/CellSize].intersects(player)){
                                  if(empty[player.x/CellSize][player.y/CellSize].isCanChange()){
                                      empty[player.x/CellSize][player.y/CellSize].setCanChange(false);
                                      count+=10;
                                  }
                               }
                       }else{
                           synchronized (x){
                               try{
                                   x.wait();
                               }catch (InterruptedException e){}
                           }
                       }
            }
        }
    });

    public Window(String name){
        try {
            Scanner scanner = new Scanner(bestFile);
            BestResult = scanner.nextInt();
        } catch (FileNotFoundException e) {
        } catch (NoSuchElementException e){}

        frame.addKeyListener(new WindowKeyListener());
        frame.setSize(LONGITUDE+CellSize, HIGH+CellSize*5);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(new PanelPaint());

        timer = new Timer(20,this);

        LoadField(name);

        player = new Player(23*CellSize,16*CellSize,CellSize);
        intelij = new Intelij(16,16,CellSize,empty);
        mob1 = new Intelij(16,32,CellSize,empty);
        mob2 = new Intelij(16,48,CellSize,empty);

        timer.start();
        MyLogic.start();
        intelij.start();
        mob1.start();
        mob2.start();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                inGame = false;
                intelij.Stop();
                mob1.Stop();
                mob2.Stop();
                SaveResult();
            }
        });

        frame.repaint();
    }

    public boolean isCanMove(int way){
        int x,y = 0; x = 0;
        if(player != null){
            x = player.x;
            y = player.y;
        }
        try {
            if (way == 1)
                if (player.intersects(list[x / CellSize + 1][y / CellSize])) return false;

            if (way == 2)
                if (player.intersects(list[x / CellSize - 1][y / CellSize])) return false;

            if (way == 3)
                if (player.intersects(list[x / CellSize][y / CellSize - 1])) return false;

            if (way == 4)
                if (player.intersects(list[x / CellSize][y / CellSize + 1])) return false;
        }catch (NullPointerException e){
            return true;
        }catch (IndexOutOfBoundsException e){
            System.out.println("OutOf");
            return false;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((player.intersects(intelij) | player.intersects(mob1) | player.intersects(mob2)) && !inVisability){
           inGame = false;
           intelij.Stop();
           mob1.Stop();
           mob2.Stop();
        }

        if (!inVisability) {
            intelij.setAimForII(player.x, player.y);  // задает цель для моба
            mob1.setAimForII(player.x, player.y);
            mob2.setAimForII(player.x, player.y);
        }

        if(inVisability) {
            inVisabilityCount++;
            if (inVisabilityCount > 200) {
                inVisability = false;
                inVisabilityCount = 0;
            }
        }

        frame.repaint();
    }

    private class WindowKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            synchronized (x){
                x.notifyAll();
            }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)way = 1;
                if (e.getKeyCode() == KeyEvent.VK_UP) way = 3;
                if (e.getKeyCode() == KeyEvent.VK_LEFT) way = 2;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) way = 4;
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    synchronized (x){
                        try{
                            x.wait();
                        }catch (InterruptedException ex){}
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    inVisability = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    sound.play();
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    sound.stop();
                }
        }
    }

    private void LoadField(String name) {
        //1 выгрузка из файла
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 18; j++) {
                if(list[i][j] != null)list[i][j] = null;
            }
        }
        File file = new File(name);
        System.out.println(name);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
        }
        while (true) {
            try {
                int aX = scanner.nextInt();
                int aY = scanner.nextInt();
                list[aX / CellSize][aY / CellSize] = new Cell(aX, aY, CellSize);
            } catch (NoSuchElementException e) {
                break;
            }
        }

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 18; j++) {
                if(list[i][j] == null)empty[i][j] = new Cell(i*CellSize,j*CellSize,CellSize);
            }
        }
    }

    // -------------------------PAINT-----------------------------------------//
    class PanelPaint extends JPanel {
        PanelPaint() {
            setSize(LONGITUDE + CellSize, HIGH + CellSize * 2 + 6);
        }

        protected void paintComponent(Graphics g) {
            for (int i = 0; i < LONGITUDE/CellSize; i++) {
                for (int j = 0; j < HIGH/CellSize; j++) {
                    if(list[i][j] != null) {
                        g.drawImage(stone,list[i][j].x, list[i][j].y,this);
                    }
                    if(empty[i][j] != null){
                        g.drawImage(simpleBlock,empty[i][j].x, empty[i][j].y,this);
                        if(empty[i][j].isCanChange())g.drawImage(bomb,empty[i][j].x, empty[i][j].y,this);
                    }
                }
            }
            if(!inVisability) {
                g.setColor(Color.YELLOW);
                g.fillRect(player.x, player.y, player.width, player.height);
                g.setColor(Color.BLUE);
                g.fillRect(player.x, player.y, player.width, player.height / 2);
                g.drawRect(player.x, player.y, player.width, player.height);
            }else{
                g.setColor(Color.BLACK);
                g.fillRect(player.x, player.y, player.width, player.height);
                g.drawRect(player.x, player.y, player.width, player.height);
            }

            if(intelij != null) {
                g.setColor(Color.RED);
                g.fillRect(intelij.x, intelij.y, intelij.width, intelij.height);
                g.setColor(Color.WHITE);
                g.fillRect(intelij.x, intelij.y, intelij.width, (intelij.height*3)/2);
                g.setColor(Color.BLUE);
                g.fillRect(intelij.x, intelij.y, intelij.width, intelij.height/3);
                g.drawRect(intelij.x, intelij.y, intelij.width, intelij.height);
            }

            if(mob1 != null) {
                g.setColor(Color.RED);
                g.drawRect(mob1.x, mob1.y, mob1.width, mob1.height);
                g.fillRect(mob1.x, mob1.y, mob1.width, mob1.height);
            }

            if(mob2 != null) {
                g.setColor(Color.RED);
                g.drawRect(mob2.x, mob2.y, mob2.width, mob2.height);
                g.fillRect(mob2.x, mob2.y, mob2.width, mob2.height);
            }

            g.setColor(Color.BLACK);
            g.drawString(count + " = count", 40,19*CellSize);
            g.drawString(BestResult + " = best", 20*CellSize,19*CellSize);
        }
    }
    public void SaveResult(){
        if(count > BestResult){
            try {
                PrintWriter printWriter = new PrintWriter("Best.txt");
                printWriter.print(count);
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void Pause(){
        pause = true;
        intelij.Stop();
        mob1.Stop();
        mob2.Stop();
    }

    public void Play(){
        pause = false;
        intelij.start();
        mob1.start();
        mob2.start();
        synchronized (x){
            x.notifyAll();
        }
    }
}