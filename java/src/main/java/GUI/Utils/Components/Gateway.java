package GUI.Utils.Components;

import lombok.Data;

import java.awt.*;

@Data
public class Gateway {

    private Point leftTop;
    private Point leftDown;
    private Point right;
    private Point leftConnection;
    private Point rightConnection;
    private int direction;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int CENTER = 3;

    public Gateway(Point leftTop, Point leftDown, Point right, int direction) {
        this.leftTop = leftTop;
        this.leftDown = leftDown;
        this.right = right;
        this.direction = direction;
        init();
    }

    private void init() {
        leftConnection = new Point((int)leftDown.getX(),(int)right.getY());
        rightConnection = right;
    }

    public void paint(Graphics graphics){
        graphics.drawPolygon(
                new int[]{(int)leftTop.getX(),(int)leftDown.getX(),(int)right.getX()},
                new int[]{(int)leftTop.getY(), (int)leftDown.getY(),(int)right.getY()},
                3);
        switch (direction){
            case UP:
                break;
            case DOWN:
                break;
            case CENTER:
                break;
        }
    }
}
