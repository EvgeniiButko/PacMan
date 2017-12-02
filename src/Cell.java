import java.awt.*;

public class Cell extends Rectangle{
    private boolean isCanChange = true;

    Cell(int x, int y,int size) {
        this.x = x;
        this.y = y;
        width = size;
        height = size;
    }

    public boolean isCanChange() {
        return isCanChange;
    }

    public void setCanChange(boolean canChange) {
        isCanChange = canChange;
    }

}
