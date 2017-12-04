import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame{
    public static JFrame UnderMenu = new JFrame();
    private Window w;
    private MapMaker m;

    Menu(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400,400);
        setResizable(false);
//        button1 = new MyButton(100,50,180,45,"ButON.png","ButOFF.png");
//        button1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                w = new Window("Map1.txt");
//                setVisible(false);
//            }
//        });
//        button2 = new MyButton(100,150,180,45,"ButON.png","ButOFF.png");
//        button2.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                w = new Window("Map1.txt");
//                setVisible(false);
//            }
//        });
//        button3 = new MyButton(100,200,180,45,"ButON.png","ButOFF.png");
//        button3.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                w = null;
//                w = new Window("Map1.txt");
//                setVisible(false);
//            }
//        });
//        add(button1);
//        add(button2);
//        add(button3);
        add(new MenuPanel());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        repaint();

 //----------------------------------------------UnderMenu---------------------------------------------//

        UnderMenu.setSize(200,200);
        UnderMenu.setResizable(false);
        UnderMenu.setVisible(false);
        JButton button = new JButton("Try again");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                w = new Window("Map1.txt");
                System.gc();
                UnderMenu.setVisible(false);
            }
        });
        JButton b = new JButton("Back to menu");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                UnderMenu.setVisible(false);
            }
        });
        UnderMenu.setLayout(new FlowLayout());
        UnderMenu.add(button);
        UnderMenu.add(b);
    }
//--------------------------------------------------------------------------------------//

}
