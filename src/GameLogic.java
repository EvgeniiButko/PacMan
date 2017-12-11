import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic implements ActionListener{

    public GameFileLogic arrays = new GameFileLogic();  //m
    public Cell[][] list = arrays.getList();
    public Cell[][] empty = arrays.getEmpty();
    private final int CellSize = 16;
    public boolean isVisible;
    public int countForVisible;
    Intelij intelij = new Intelij(16,16,CellSize,empty);
    Intelij mob1 = new Intelij(16,32,CellSize,empty);
    Intelij mob2 = new Intelij(16,48,CellSize,empty);
    public Menu linkOnMenu = null;   //m
    public int count;
    Player player = new Player(23*CellSize,16*CellSize,CellSize);
    private boolean inGame = true;
    private boolean pause = false;
    public int way = 0;
    Object x = new Object();
    private Timer timer = new Timer(50,this);
    private String Name;
    private int MaxValue;

    Thread MyLogic = new Thread(new Runnable() {
        @Override
        public void run() {
            while (inGame) {
                if (isCanMove(way) && !pause) {
                    int myway = way;
                    for (int i = 0; i < 8; i++) {
                        player.move(myway);
                        try {
                            Thread.sleep(45);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (empty[player.x / CellSize][player.y / CellSize].intersects(player)) {
                        if (empty[player.x / CellSize][player.y / CellSize].isCanChange()) {
                            empty[player.x / CellSize][player.y / CellSize].setCanChange(false);
                            count += 10;
                            //                       sound.play(0);
                        }
                    }
                } else {
                    synchronized (x) {
                        try {
                            x.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
    });

    public GameLogic(String name, Menu link){
        linkOnMenu = link;
        Name = name;
        MyLogic.start();
        Start();

        MaxValue = arrays.LoadField(name);
    }
    public void actionPerformed(ActionEvent e){
        if(isVisible){
            countForVisible++;
            if(countForVisible > 80){
                countForVisible = 0;
                isVisible = false;
            }
        }
        if(!isVisible && (player.intersects(intelij) | player.intersects(mob1) | player.intersects(mob2))){
            if(linkOnMenu.getLife() <= 0) {
                arrays.SaveResult(count + linkOnMenu.getResult());
                inGame = false;
                intelij.Stop();
                mob1.Stop();
                mob2.Stop();
                timer.stop();
                //           sound.play(1);
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ex) {
                }
                linkOnMenu.ChangeForSecondMenu();
            }else{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){}
                linkOnMenu.setLife(linkOnMenu.getLife()-1);
                isVisible = true;
            }
        }
        if(count == MaxValue*10){
            linkOnMenu.setResult(linkOnMenu.getResult()+count);
            inGame = false;
            intelij.Stop();
            mob1.Stop();
            mob2.Stop();
            timer.stop();
            if(Name.equals("MAP2.png")){
                linkOnMenu.ChangeForSecondMenu();
            }else{linkOnMenu.ChangeForTherdMenu();}
        }
        if(!isVisible){
            if (player.x == intelij.x || player.y == intelij.y) intelij.setAimForII(player.x, player.y); // задает цель для моба
            if (player.x == mob1.x || player.y == mob1.y) mob1.setAimForII(player.x, player.y);
            if (player.x == mob2.x || player.y == mob2.y) mob2.setAimForII(player.x, player.y); //проеверяет есть ли необходимость задать цель (видит ли на данный момент моб игрока)
        }
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
            return false;
        }
        return false;
    }

    public void Stop(){
        inGame = false;
        intelij.Stop();
        mob1.Stop();
        mob2.Stop();
        timer.stop();
    }
    public void Pause(){
        intelij.Stop();
        mob1.Stop();
        mob2.Stop();
        timer.stop();
    }
    public void Start(){
        intelij.start();
        mob1.start();
        mob2.start();
        timer.start();
    }
}
