package com.duplex_channel.GUI.Utils.Components;

import com.duplex_channel.GUI.Utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics;

@Setter @Getter
public class CommunicationСhannel {
    private int d;
    private int x;
    private int y;
    private Point rightConnection;
    private Point leftConnection;
    private Point center;
    private Graphics graphics;
    private String title;
    private int workTime;
    private boolean busy;
    private int statistic_workTime = 0;
    private int package_count = 0;

    public void workTime_statistic_inc(){
        statistic_workTime++;
    }

    public void package_count_int(){
        package_count++;
    }

    public CommunicationСhannel(int d, int x, int y, String title) {
        this.d = d;
        this.x = x;
        this.y = y;
        this.title = title;
        init();
    }

    private void init() {
        busy = false;
        rightConnection = new Point(x+d,y+d/2);
        leftConnection = new Point(x,y+d/2);
        center = new Point(x+d/2,y+d/2);
    }

    public void paint(Graphics graphics){
        graphics.drawOval(x,y,d,d);
        graphics.drawString(title, x + Utils.SIZE / 2 - Utils.BACKLASH, y + Utils.SIZE / 2 + Utils.BACKLASH*2);
    }

    public void paint(Graphics graphics, int i){
        graphics.drawString(busy?"busy (" + i +"/"+workTime+")":"free",x- Utils.BACKLASH,y+ Utils.SIZE+ Utils.BACKLASH*2);
        graphics.drawOval(x,y,d,d);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CommunicationСhannel &&
                ((CommunicationСhannel) obj).x == this.x &&
                ((CommunicationСhannel) obj).y == this.y &&
                ((CommunicationСhannel) obj).title.equals(this.title);
    }
}