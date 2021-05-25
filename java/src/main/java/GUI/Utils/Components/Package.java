package GUI.Utils.Components;

import GUI.Utils.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
@NoArgsConstructor
public class Package {

    public static final int PLUS = 1;
    public static final int MINUS = 2;

    private Point position;

    public Package(java.awt.Point rightConnection, int priority) {
    }

    public void setPosition(Point position){
        this.position = new Point((int)position.getX(),(int)position.getY());
    }

    private boolean wait;
    private int priority;
    private Trace trace;
    private int workTime = 0;
    private CommunicationСhannel workPlace;
    private boolean decline;

    public void setTraceToNull(){
        this.trace = null;
    }

    public void setTrace(Trace trace){
        if (trace == null)
            return;
        this.trace = new Trace(trace.getPoints());
    }

    public void workTimeInc(){
        workTime++;
    }

    public Package(Point position, int priority) {
        this.priority = priority;
        this.position = new Point((int)position.getX(), (int)position.getY());
    }

    public void print(Graphics graphics,int i) {
        graphics.setColor(Color.BLUE);
        if (priority == 2)
            graphics.setColor(Color.RED);
        graphics.drawOval((int)position.getX()-Utils.TRANSACT_SIZE/2, (int)position.getY() - Utils.TRANSACT_SIZE / 2, Utils.TRANSACT_SIZE, Utils.TRANSACT_SIZE);
        graphics.fillOval((int)position.getX()-Utils.TRANSACT_SIZE/2, (int)position.getY() - Utils.TRANSACT_SIZE / 2, Utils.TRANSACT_SIZE, Utils.TRANSACT_SIZE);
        graphics.setColor(Color.BLACK);
        graphics.drawString(i+"",(int)position.getX()-Utils.TRANSACT_SIZE/2-Utils.BACKLASH,(int)position.getY() - Utils.TRANSACT_SIZE / 2);
        graphics.setColor(Color.BLUE);
    }

    public void updateX(int i) {
        if (i == PLUS)
            position.setX(position.getX() + Utils.STEP);
        else
            position.setX(position.getX() - Utils.STEP);
    }

    public void updateY(int i) {
        if (i == MINUS)
            position.setY(position.getY() - Utils.STEP);
        else
            position.setY(position.getY() + Utils.STEP);
    }

    public void deleteFirstTracePoint(){
        trace.getPoints().remove(0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Package && this.getPosition().equals(((Package) obj).getPosition());
    }
}
