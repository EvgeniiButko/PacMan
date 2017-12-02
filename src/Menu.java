import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
    private Window w;
    private MapMaker m;
    MyButton button1;
    MyButton button2;
    MyButton button3;
    private Image img = new ImageIcon("FUCK.png").getImage();
    private JPanel jPanel = new JPanel();

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "   Play   "){
            w = new Window("lvl1.txt");
            //setVisible(false);
        }
        if(e.getActionCommand() == "Remake map"){
            m = new MapMaker();
        }
        if(e.getActionCommand() == "   Exit   "){
            System.exit(0);
        }
        if(e.getActionCommand() == "Play on my map"){
            w = new Window("MAP2.txt");
        }
    }

    Menu(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400,400);
        setResizable(false);
        button1 = new MyButton(100,50,180,45,"ButON.png","ButOFF.png");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                w = new Window("lvl1.txt");
            }
        });
        button2 = new MyButton(100,150,180,45,"ButON.png","ButOFF.png");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                w = new Window("Map1.txt");
            }
        });
        button3 = new MyButton(100,200,180,45,"ButON.png","ButOFF.png");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                w = new Window("Map1.txt");
            }
        });
        add(button1);
        add(button2);
        add(button3);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        repaint();
    }


}
