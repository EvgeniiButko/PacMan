import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    public GameLogic gameLogic;
    public static int CellSize = 16;
    private int LONGITUDE = CellSize * 25;
    private int HIGH = CellSize * 18;
    private Image imagePlayer = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("player.png"))).getImage();
    private Image mobImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("mob.png"))).getImage();
    private Image bomb = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("bomb.png"))).getImage();
    private Image stone = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Камень.png"))).getImage();
    private Image simpleBlock = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("field.png"))).getImage();
    Timer timer;    //m
    private PaintPanel paintPanel = new PaintPanel(); //m

    public GamePanel(String name, Menu link){
        setFocusable(true);
        gameLogic = new GameLogic(name,link);

        timer = new Timer(20,this);

        timer.start();

        add(paintPanel);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    // —-----------------------PAINT-----------------------------------------//
    class PaintPanel extends JPanel{
        protected void paintComponent (Graphics g){
        super.paintComponent(g);
            for (int i = 0; i < LONGITUDE/CellSize; i++) {
                for (int j = 0; j < HIGH/CellSize; j++) {
                    if(gameLogic.list[i][j] != null) {
                        g.drawImage(stone,gameLogic.list[i][j].x, gameLogic.list[i][j].y,this);
                    }
                    if(gameLogic.empty[i][j] != null){
                        g.drawImage(simpleBlock,gameLogic.empty[i][j].x, gameLogic.empty[i][j].y,this);
                        if(gameLogic.empty[i][j].isCanChange())g.drawImage(bomb,gameLogic.empty[i][j].x, gameLogic.empty[i][j].y,this);
                    }
                }
            }
            if(gameLogic.player != null)g.drawImage(imagePlayer,gameLogic.player.x,gameLogic.player.y,gameLogic.player.width,gameLogic.player.height,this);


            if(gameLogic.intelij != null) {
                g.drawImage(mobImage,gameLogic.intelij.x,gameLogic.intelij.y,gameLogic.intelij.width,gameLogic.intelij.height,this);
            }

            if(gameLogic.mob1 != null) {
                g.drawImage(mobImage,gameLogic.mob1.x,gameLogic.mob1.y,gameLogic.mob1.width,gameLogic.mob1.height,this);
            }

            if(gameLogic.mob2 != null) {
                g.drawImage(mobImage,gameLogic.mob2.x,gameLogic.mob2.y,gameLogic.mob2.width,gameLogic.mob2.height,this);
            }

            g.setColor(Color.BLACK);
            g.drawString((gameLogic.linkOnMenu.getResult()+gameLogic.count) + " = count", 40,19*CellSize);
            g.drawString(gameLogic.arrays.getBestResult() + " = best", 20*CellSize,19*CellSize);
            g.drawString(gameLogic.linkOnMenu.getLife() + " lifes",10*CellSize,19*CellSize);
            if(gameLogic.isVisible)g.drawString((200-gameLogic.countForVisible)/10+" invisability",10*CellSize,20*CellSize);
         }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPanel.setBounds(0,0,400,400);
    }


    public void Stop(){
        timer.stop();
    }
    public void Pause(){
        timer.stop();
    }
    public void Start(){
        timer.start();
    }
}