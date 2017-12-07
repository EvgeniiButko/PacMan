import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener {
    public static int CellSize = 16;
    private int LONGITUDE = CellSize * 25;
    private int HIGH = CellSize * 18;
    private File bestFile = new File("Best.txt");
    private FieldArrays arrays = new FieldArrays();
    private Cell[][] list = arrays.getList();
    private Cell[][] empty = arrays.getEmpty();
    Player player ;
    private boolean inGame = true;
    private boolean pause = false;
    public int way = 0;
    Object x = new Object();
    private Intelij intelij;
    private Intelij mob1;
    private Intelij mob2;
    private int BestResult = 0;
    private Image bomb = new ImageIcon("bomb.png").getImage();
    private Image stone = new ImageIcon("Камень.png").getImage();
    private Image simpleBlock = new ImageIcon("field.png").getImage();
    Timer timer;
    private Sound sound = new Sound("Beep4.wav","Broken_Robot4.wav");
    private Menu linkOnMenu = null;
    private int count;
    private PaintPanel paintPanel = new PaintPanel();
    private String Name;
    private int MaxValue;

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
                            sound.play(0);
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

    public Game(String name, Menu link){
        linkOnMenu = link;
        setFocusable(true);
        Name = name;

        try {
            Scanner scanner = new Scanner(bestFile);
            BestResult = scanner.nextInt();
        } catch (FileNotFoundException e) {
        } catch (NoSuchElementException e){}

        timer = new Timer(20,this);

        MaxValue = arrays.LoadField(name);

        player = new Player(23*CellSize,16*CellSize,CellSize,"player.png");
        intelij = new Intelij(16,16,CellSize,empty,"mob.png");
        mob1 = new Intelij(16,32,CellSize,empty,"mob.png");
        mob2 = new Intelij(16,48,CellSize,empty,"mob.png");

        timer.start();
        MyLogic.start();
        intelij.start();
        mob1.start();
        mob2.start();

        add(paintPanel);
        repaint();
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
        if(player.intersects(intelij) | player.intersects(mob1) | player.intersects(mob2)){
            SaveResult();
            inGame = false;
            intelij.Stop();
            mob1.Stop();
            mob2.Stop();
            timer.stop();
            sound.play(1);
            try{
                Thread.sleep(2500);
            }catch(InterruptedException ex){}
            linkOnMenu.ChangeForSecondMenu();
        }
        if(count == MaxValue*10){
            SaveResult();
            inGame = false;
            intelij.Stop();
            mob1.Stop();
            mob2.Stop();
            timer.stop();
            linkOnMenu.ChangeForTherdMenu();
              if(Name.equals("MAP2.png"))linkOnMenu.ChangeForSecondMenu();
        }
            if(player.x == intelij.x || player.y == intelij.y)intelij.setAimForII(player.x, player.y); // задает цель для моба
            if(player.x == mob1.x || player.y == mob1.y)mob1.setAimForII(player.x, player.y);
            if(player.x == mob2.x || player.y == mob2.y)mob2.setAimForII(player.x, player.y); //проеверяет есть ли необходимость задать цель (видит ли на данный момент моб игрока)

        repaint();
    }

    // —-----------------------PAINT-----------------------------------------//
    class PaintPanel extends JPanel{
        protected void paintComponent (Graphics g){
        super.paintComponent(g);
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
                g.drawImage(player.image,player.x,player.y,player.width,player.height,this);


            if(intelij != null) {
                g.drawImage(intelij.image,intelij.x,intelij.y,intelij.width,intelij.height,this);
            }

            if(mob1 != null) {
                g.drawImage(mob1.image,mob1.x,mob1.y,mob1.width,mob1.height,this);
            }

            if(mob2 != null) {
                g.drawImage(mob2.image,mob2.x,mob2.y,mob2.width,mob2.height,this);
            }

            g.setColor(Color.BLACK);
            g.drawString(count + " = count", 40,19*CellSize);
            g.drawString(BestResult + " = best", 20*CellSize,19*CellSize);
         }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPanel.setBounds(0,0,400,400);
    }

    public void SaveResult() {
        if (linkOnMenu.count > BestResult) {
            try {
                PrintWriter printWriter = new PrintWriter("Best.txt");
                printWriter.print(linkOnMenu.count);
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void Stop(){
        inGame = false;
        intelij.Stop();
        mob1.Stop();
        mob2.Stop();
        timer.stop();
    }

}