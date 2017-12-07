import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FieldArrays {
    private Cell[][] list = new Cell[25][18];
    private Cell[][] empty = new Cell[25][18];
    private int couunt;

    public int LoadField(String name) {
//1 выгрузка из файла
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 18; j++) {
                if (list[i][j] != null) list[i][j] = null;
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
                list[aX / Game.CellSize][aY / Game.CellSize] = new Cell(aX, aY, Game.CellSize);
            } catch (NoSuchElementException e) {
                break;
            }catch (NullPointerException e){}
        }
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 18; j++) {
                if(list[i][j] == null){
                    empty[i][j] = new Cell(i* Game.CellSize,j* Game.CellSize, Game.CellSize);
                    couunt++;
                }
            }
        }
        return couunt;
    }

    public Cell[][] getEmpty() {
        return empty;
    }

    public Cell[][] getList() {
        return list;
    }
}
