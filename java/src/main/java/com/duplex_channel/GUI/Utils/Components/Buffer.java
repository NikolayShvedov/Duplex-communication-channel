package com.duplex_channel.GUI.Utils.Components;

import com.duplex_channel.GUI.Utils.Utils;
import lombok.Data;

import java.awt.*;

@Data
public class Buffer {

    private Point leftConnection;
    private Point rightConnection;
    private Point center;
    private int maxTransactCount;
    private int currentTransactCount;

    public void currentTransactCountInc(){
        currentTransactCount++;
    }

    public void currentTransactCountDec(){
        if (currentTransactCount > 0)
            currentTransactCount--;
    }

    public Buffer(Point leftConnection) {
        this.leftConnection = new Point((int)leftConnection.getX(), (int)leftConnection.getY());
        init();
    }
    private void init(){
        rightConnection = new Point();
        rightConnection.setX(leftConnection.getX()+Utils.SIZE);
        rightConnection.setY(leftConnection.getY());
        center = new Point((int)leftConnection.getX()+Utils.SIZE/2,(int)leftConnection.getY());
    }

    public void paint(Graphics graphics){
        graphics.drawString(currentTransactCount+"/"+maxTransactCount,(int)center.getX()-Utils.BACKLASH,(int)center.getY()-Utils.SIZE/2-Utils.BACKLASH);
        graphics.drawPolyline(
                new int[]{
                        (int)leftConnection.getX()-Utils.BACKLASH,
                        (int)leftConnection.getX()+Utils.SIZE,
                        (int)leftConnection.getX()+Utils.SIZE,
                        (int)leftConnection.getX()-Utils.BACKLASH,
                },
                new int[]{
                        (int)leftConnection.getY()-Utils.SIZE/2,
                        (int)leftConnection.getY()-Utils.SIZE/2,
                        (int)leftConnection.getY()+Utils.SIZE/2,
                        (int)leftConnection.getY()+Utils.SIZE/2,

                },
                4);
        graphics.drawLine((int)leftConnection.getX(),(int)leftConnection.getY()-Utils.SIZE/2,
                (int)leftConnection.getX(),(int)leftConnection.getY()+Utils.SIZE/2);

        graphics.drawLine((int)leftConnection.getX()+Utils.SIZE/4,(int)leftConnection.getY()-Utils.SIZE/2,
                (int)leftConnection.getX()+Utils.SIZE/4,(int)leftConnection.getY()+Utils.SIZE/2);

        graphics.drawLine((int)leftConnection.getX()+Utils.SIZE/2,(int)leftConnection.getY()-Utils.SIZE/2,
                (int)leftConnection.getX()+Utils.SIZE/2,(int)leftConnection.getY()+Utils.SIZE/2);

    }

}
