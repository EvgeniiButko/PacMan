import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MapMaker implements MouseListener,ActionListener {
    private final int CellSize = 16;
    private final int HIGH = CellSize * 18;
    private final int LONGITUDE = CellSize * 25;
    private JFrame frame = new JFrame();
    private Cell_ForMapMaking[][] list = new Cell_ForMapMaking[LONGITUDE/CellSize][HIGH/CellSize];
    private ArrayList<Cell_ForMapMaking> forFile = new ArrayList<>();
    private int MouseX;
    private int MouseY;
    private Button button = new Button("SAVE");
    private PrintWriter printWriter;
    private String fileName = "MAP2.txt";

    public MapMaker(){
        Load();
        LoadDefaultBlocks();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        frame.setSize(LONGITUDE + CellSize, HIGH + CellSize * 2 + 40);
        frame.setVisible(true);
        frame.addMouseListener(this);
        frame.repaint();
        frame.add(new PanelPaint(),BorderLayout.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadInFile();
            }
        });
        frame.add(button,BorderLayout.SOUTH);
        Timer timer = new Timer(200,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.repaint();
    }


    private class PanelPaint extends JPanel {
        PanelPaint(){
            setSize(LONGITUDE + CellSize, HIGH + CellSize * 2 + 6);
        }
        protected void paintComponent(Graphics g) {
            for (int i = 0; i < list.length; i++) {
                for (int j = 0; j < list[i].length; j++) {
                    g.drawImage(list[i][j].getImage(),list[i][j].getX(),list[i][j].getY(),this);

                }
            }
        }
    }

    private void Load(){              //метод для зарисовки поля
        for (int i = 0; i < LONGITUDE/CellSize; i++) {
            for (int j = 0; j < HIGH/CellSize; j++) {
                list[i][j] = new Cell_ForMapMaking(i*CellSize,j*CellSize);
            }
        }
    }

    private void LoadInFile(){
        //метод для сохранения в файл
        try{
            printWriter = new PrintWriter(fileName);
            for (int i = 0; i < forFile.size(); i++) {
                if(forFile.get(i).getCount()%2 != 0) {
                    printWriter.println(forFile.get(i).getX());
                    printWriter.println(forFile.get(i).getY());
                }
            }
            printWriter.close();
        }catch (FileNotFoundException e){}
    }

    public void LoadDefaultBlocks(){
        for (int i = 0; i < LONGITUDE/CellSize; i++) {
            list[i][0].incImage();
            list[i][HIGH/CellSize-1].incImage();
            forFile.add(list[i][HIGH/CellSize-1]);
            forFile.add(list[i][0]);
        }
        for (int i = 0; i < HIGH/CellSize; i++) {
            list[0][i].incImage();
            list[LONGITUDE/CellSize-1][i].incImage();
            forFile.add(list[LONGITUDE/CellSize-1][i]);
            forFile.add(list[0][i]);
        }
    }

    private class Cell_ForMapMaking{          // класс для клетки
        private int x;
        private int y;
        private int count = 0;
        private ImageIcon imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("forMapMaker.png")));
        private  ImageIcon imageIcon2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Yellow.png")));
        private boolean img = true;

        public void incImage(){
            img = !img;
            count++;
        }


        public int getCount(){
            return count;
        }
        public Cell_ForMapMaking(int x,int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Image getImage() {
            if(img){
                return imageIcon.getImage();
            }else{
                return imageIcon2.getImage();
            }
        }
    }

    public void Action(int a, int b){
        a -= 9;
        a = a/CellSize;
        b = b/CellSize;
        b -= 2;
         try {
             list[a][b].incImage();
             forFile.add(list[a][b]);
         }catch (ArrayIndexOutOfBoundsException e){}
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MouseX = e.getX();
        MouseY = e.getY();
        Action(MouseX,MouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public static void main(String[] args) {
        MapMaker m = new MapMaker();
    }
}
