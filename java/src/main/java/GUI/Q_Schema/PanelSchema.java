package GUI.Q_Schema;

import GUI.GUIStatistic.GUIStatistic;
import GUI.Utils.Components.*;
import GUI.Utils.Components.Package;
import GUI.Utils.Components.Point;
import GUI.Utils.Utils;
import Statistic.Statistic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelSchema extends JPanel implements Runnable {

    private int simulationTime;
    int time = 0;

    // компоненты q-схемы
    private CommunicationСhannel itemA;
    private CommunicationСhannel itemB;
    private Gateway firstGateway;
    private Gateway secondGateway;
    private CommunicationСhannel channelAB;
    private CommunicationСhannel channelBA;
    private CommunicationСhannel sputnikLine;
    private Buffer bufferA;
    private Buffer bufferB;
    private End itemEnd;
    private ArrayList<Package> packages;
    private Trace first = new Trace();
    private Trace second = new Trace();

    private Arrow betweenItemAAndBufferA;
    private Arrow betweenBufferAAndFirstGateway;
    private Arrow betweenFirstGatewayAndChannelAB;
    private Arrow betweenFirstGatewayAndSputnikLine;
    private Arrow betweenChannelABAndEndItem;
    private Arrow betweenSputnikLineAndEndItem;

    private Arrow betweenItemBAndBufferB;
    private Arrow betweenBufferBAndSecondGateway;
    private Arrow betweenSecondGatewayAndChannelBA;
    private Arrow betweenSecondGatewayAndSputnikLine;
    private Arrow betweenChannelBAAndEndItem;
    //***

    private boolean firstInit = true;
    private int transactCountA = 0;
    private int transactCountB = 0;
    private int doneTransactCount = 0;
    private int declineCount = 0;
    private int current_run;

    public PanelSchema() {
        setBackground(Color.WHITE);
        packages = new ArrayList<>();
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < Utils.RUN_COUNT; i++) {
            current_run = i + 1;
            simulationTime = Utils.TIME;

            if (!firstInit) {
                channelAB.setWorkTime(10);
                channelBA.setWorkTime(10);
                sputnikLine.setWorkTime((int) (10 - Utils.DEVIATION_SPUTNIK_LINE + Math.random() * Utils.DEVIATION_SPUTNIK_LINE * 2));
            }

            while (simulationTime != 0) {
                super.repaint();
                try {
                    Thread.sleep(Utils.DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (Package transact : packages) {
                if (transact.getPosition().getX() > itemEnd.getX())
                    doneTransactCount++;
                if (transact.isDecline())
                    declineCount++;
            }
            reInit();
        }

        Statistic.instance.addPackageCountA(transactCountA);
        Statistic.instance.addPackageCountB(transactCountB);
        Statistic.instance.addDonePackages(doneTransactCount);
        Statistic.instance.addDeclineTransactCount(declineCount);
        Statistic.instance.addCommunicationСhannel(channelAB);
        Statistic.instance.addCommunicationСhannel(channelBA);
        Statistic.instance.addCommunicationСhannel(sputnikLine);
        new GUIStatistic();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (firstInit) {
            init();
        }
        printStatistic(g);
        printMyComponents(g);

        if (time % (int) (Utils.GENERATE_TIME - Utils.DEVIATION_GENERATE_TIME + Math.random() * Utils.DEVIATION_GENERATE_TIME * 2) == 0 || time == 0) {
            Package newTransactA = new Package(itemA.getRightConnection(), 1);
            Package newTransactB = new Package(itemB.getRightConnection(), 1);
            packages.add(newTransactA);
            transactCountA++;
            packages.add(newTransactB);
            transactCountB++;
            time = 0;
        }

        if (time > (int) (Utils.GENERATE_TIME - Utils.DEVIATION_GENERATE_TIME + Math.random() * Utils.DEVIATION_GENERATE_TIME * 2)) {
            Package newTransactA = new Package(itemA.getRightConnection(), 1);
            Package newTransactB = new Package(itemB.getRightConnection(), 1);
            packages.add(newTransactA);
            transactCountA++;
            packages.add(newTransactB);
            transactCountB++;
            time = 0;
        }

        time++;
        simulationTime--;

        for (Package pack : packages) {

            if (pack.getPosition().getX() < itemEnd.getX())
                pack.print(g, packages.indexOf(pack));

            //первая развилка
            if (pack.getPosition().equals(firstGateway.getRightConnection())) {
                if (channelAB.isBusy()) {
                    pack.setTrace(second);
                    pack.setWorkPlace(sputnikLine);
                    pack.setDecline(true);
                } else {
                    pack.setTrace(first);
                    pack.setWorkPlace(channelAB);
//                    channelAB.setBusy(true);
                }

            }

            //вторая развилка
            if (pack.getPosition().equals(secondGateway.getRightConnection())) {
                if (channelBA.isBusy()) {
                      pack.setTrace(first);
                    pack.setWorkPlace(channelBA);
                } else {
                    pack.setTrace(second);
                    pack.setWorkPlace(sputnikLine);
                    pack.setDecline(true);
                    channelBA.setBusy(true);
                }

            }

            //если у пакета нет своего пути, то просто смещаем его на шаг
            if (pack.getTrace() == null)
                pack.updateX(Package.PLUS);
            else
                runTrace(pack, pack.getWorkPlace(), g);

        }

        cleanResource();

    }

    private void cleanResource(){
        boolean bK1 = false;
        boolean bK2 = false;
        boolean bK3 = false;
        for (Package pack: packages){
            if (pack.getPosition().equals(channelAB.getCenter())){
                bK1 = true;
                break;
            }
            if (pack.getPosition().equals(channelBA.getCenter())){
                bK2 = true;
                break;
            }
            if (pack.getPosition().equals(sputnikLine.getCenter())){
                bK3 = true;
                break;
            }
        }
        if (bK1)
            channelAB.setBusy(false);
        if (bK2)
            channelBA.setBusy(false);
        if (bK3)
            sputnikLine.setBusy(false);
    }

    private void printStatistic(Graphics g) {
        String s;
        g.drawString("Время обработки канала А - В: " + channelAB.getWorkTime(), 10, 20);
        g.drawString("Время обработки канала В - А: " + channelBA.getWorkTime(), 10, 30);
        g.drawString("Время обработки спутниковой линии связи: " + sputnikLine.getWorkTime(), 10, 40);
        s = "Оставшееся время: " + simulationTime;
        g.drawString(s, 10, 10);
        g.drawString("МОДЕЛИРОВАНИЕ № " + current_run, Utils.WIDTH / 2, 10);
    }

    private void init() {
        itemA = new CommunicationСhannel(Utils.SIZE, (int) (Utils.HEIGHT * 0.1), (int) (Utils.WIDTH / 4 - 20), "И1");
        bufferA = new Buffer(new Point((int)itemA.getRightConnection().getX() + Utils.DIFFERENCE, (int)itemA.getRightConnection().getY()));
        bufferA.setMaxTransactCount(Utils.QUEUE_PACKAGE_COUNT);
        firstGateway = new Gateway(
                new Point(bufferA.getRightConnection().getX() + Utils.DIFFERENCE, bufferA.getRightConnection().getY() - Utils.SIZE / 2),
                new Point(bufferA.getRightConnection().getX() + Utils.DIFFERENCE, bufferA.getRightConnection().getY() + Utils.SIZE / 2),
                new Point(bufferA.getRightConnection().getX() + Utils.DIFFERENCE + Utils.SIZE, bufferA.getRightConnection().getY()),
                Gateway.CENTER);
        channelAB = new CommunicationСhannel(Utils.SIZE, firstGateway.getRightConnection().getX() + Utils.DIFFERENCE,
                firstGateway.getRightConnection().getY() - Utils.SIZE / 2, "К1");
        sputnikLine = new CommunicationСhannel(Utils.SIZE, firstGateway.getRightConnection().getX() + Utils.DIFFERENCE,
                firstGateway.getRightConnection().getY() - Utils.SIZE * 2 + 130, "К3");
        itemEnd = new End((int)channelAB.getRightConnection().getX() + Utils.DIFFERENCE, (int)channelAB.getRightConnection().getY());

        betweenItemAAndBufferA = new Arrow(itemA.getRightConnection(), bufferA.getLeftConnection(), Arrow.RIGHT);
        betweenBufferAAndFirstGateway = new Arrow(bufferA.getRightConnection(), firstGateway.getLeftConnection(), Arrow.RIGHT);
        betweenFirstGatewayAndChannelAB = new Arrow(firstGateway.getRightConnection(), channelAB.getLeftConnection(), Arrow.RIGHT);

        ArrayList<Point> pointsFirstGatewayAndSputnikLine = new ArrayList<>();
        pointsFirstGatewayAndSputnikLine.add(firstGateway.getRightConnection());
        pointsFirstGatewayAndSputnikLine.add(new Point(firstGateway.getRightConnection().getX(), sputnikLine.getLeftConnection().getY()));
        pointsFirstGatewayAndSputnikLine.add(sputnikLine.getLeftConnection());
        betweenFirstGatewayAndSputnikLine = new Arrow(pointsFirstGatewayAndSputnikLine, Arrow.RIGHT);

        betweenChannelABAndEndItem = new Arrow(channelAB.getRightConnection(), itemEnd.getConnection(), Arrow.RIGHT);

        ArrayList<Point> pointsK3AndEnd = new ArrayList<>();
        pointsK3AndEnd.add(sputnikLine.getRightConnection());
        pointsK3AndEnd.add(new Point(sputnikLine.getRightConnection().getX() + Utils.BACKLASH * 3, sputnikLine.getRightConnection().getY()));
        pointsK3AndEnd.add(new Point(sputnikLine.getRightConnection().getX() + Utils.BACKLASH * 3, channelAB.getRightConnection().getY()));
        pointsK3AndEnd.add(itemEnd.getConnection());
        betweenSputnikLineAndEndItem = new Arrow(pointsK3AndEnd, Arrow.RIGHT);

        itemB = new CommunicationСhannel(Utils.SIZE, (int) (Utils.HEIGHT * 0.1), (int) (Utils.WIDTH / 4 + 120), "И2");
        bufferB = new Buffer(new Point((int)itemB.getRightConnection().getX() + Utils.DIFFERENCE, (int)itemB.getRightConnection().getY()));
        bufferB.setMaxTransactCount(Utils.QUEUE_PACKAGE_COUNT);
        secondGateway = new Gateway(
                new Point(bufferB.getRightConnection().getX() + Utils.DIFFERENCE, bufferB.getRightConnection().getY() - Utils.SIZE / 2),
                new Point(bufferB.getRightConnection().getX() + Utils.DIFFERENCE, bufferB.getRightConnection().getY() + Utils.SIZE / 2),
                new Point(bufferB.getRightConnection().getX() + Utils.DIFFERENCE + Utils.SIZE, bufferB.getRightConnection().getY()),
                Gateway.CENTER);

        channelBA = new CommunicationСhannel(Utils.SIZE, secondGateway.getRightConnection().getX() + Utils.DIFFERENCE,
                secondGateway.getRightConnection().getY() - Utils.SIZE / 2, "К2");


        betweenItemBAndBufferB = new Arrow(itemB.getRightConnection(), bufferB.getLeftConnection(), Arrow.RIGHT);
        betweenBufferBAndSecondGateway = new Arrow(bufferB.getRightConnection(), secondGateway.getLeftConnection(), Arrow.RIGHT);
        betweenSecondGatewayAndChannelBA = new Arrow(secondGateway.getRightConnection(), channelBA.getLeftConnection(), Arrow.RIGHT);

        ArrayList<Point> pointsSTAndK3 = new ArrayList<>();
        pointsSTAndK3.add(secondGateway.getRightConnection());
        pointsSTAndK3.add(new Point(secondGateway.getRightConnection().getX(), sputnikLine.getLeftConnection().getY()));
        pointsSTAndK3.add(sputnikLine.getLeftConnection());
        betweenSecondGatewayAndSputnikLine = new Arrow(pointsSTAndK3, Arrow.RIGHT);

        ArrayList<Point> pointsK2AndEnd = new ArrayList<>();
        pointsK2AndEnd.add(channelBA.getRightConnection());
        pointsK2AndEnd.add(new Point(channelBA.getRightConnection().getX() + Utils.BACKLASH * 3, channelBA.getRightConnection().getY()));
        pointsK2AndEnd.add(new Point(channelBA.getRightConnection().getX() + Utils.BACKLASH * 3, channelAB.getRightConnection().getY()));
        pointsK2AndEnd.add(itemEnd.getConnection());
        betweenChannelBAAndEndItem = new Arrow(pointsK2AndEnd, Arrow.RIGHT);

        first.addArrow(betweenFirstGatewayAndChannelAB);
        first.addArrow(betweenSecondGatewayAndChannelBA);

        second.addArrow(betweenFirstGatewayAndSputnikLine);
        second.addArrow(betweenSecondGatewayAndSputnikLine);

        firstInit = false;

        channelAB.setWorkTime(10);
        channelBA.setWorkTime(10);
        sputnikLine.setWorkTime((int) (10 - Utils.DEVIATION_SPUTNIK_LINE + Math.random() * Utils.DEVIATION_SPUTNIK_LINE * 2));
    }

    private void reInit() {
        packages = new ArrayList<>();
        channelAB.setBusy(false);
        channelBA.setBusy(false);
        sputnikLine.setBusy(false);
        bufferA.setCurrentTransactCount(0);
        bufferB.setCurrentTransactCount(0);
    }

    private void printMyComponents(Graphics g) {
        g.setColor(Color.BLACK);
        itemA.paint(g);
        itemB.paint(g);
        firstGateway.paint(g);
        secondGateway.paint(g);
        bufferA.paint(g);
        bufferB.paint(g);
        channelAB.paint(g);
        channelBA.paint(g);
        sputnikLine.paint(g);
        itemEnd.paint(g);
        betweenItemAAndBufferA.paint(g);
        betweenBufferAAndFirstGateway.paint(g);
        betweenFirstGatewayAndChannelAB.paint(g);
        betweenFirstGatewayAndSputnikLine.paint(g);
        betweenChannelABAndEndItem.paint(g);
        betweenSputnikLineAndEndItem.paint(g);
        betweenItemBAndBufferB.paint(g);
        betweenBufferBAndSecondGateway.paint(g);
        betweenSecondGatewayAndChannelBA.paint(g);
        betweenSecondGatewayAndSputnikLine.paint(g);
        betweenChannelBAAndEndItem.paint(g);
    }

    private void runTrace(Package transact, CommunicationСhannel communicationСhannel, Graphics g) {
        //если у нас в пути ничего нет, то помечаем его нулем и выходим
        if (transact.getTrace().getPoints().size() == 0) {
            transact.setTraceToNull();
            return;
        }

        if (communicationСhannel != null) {

            if (channelAB.equals(communicationСhannel)) {
                transact.setPriority(2);
                transact.setPosition(communicationСhannel.getCenter());
            }
            if (channelBA.equals(communicationСhannel)) {
                transact.setPriority(2);
                transact.setPosition(communicationСhannel.getCenter());
            }
            if (sputnikLine.equals(communicationСhannel)) {
                transact.setPriority(1);
                transact.setPosition(communicationСhannel.getCenter());
            }

            for (int i = 0; i < transact.getTrace().getPoints().size(); i++) {
                if (transact.getPosition().getX() > transact.getTrace().getPoints().get(0).getX()) {
                    transact.deleteFirstTracePoint();
                    i--;
                }
            }


            if (communicationСhannel.getCenter().equals(transact.getPosition())) {
                transact.workTimeInc();
                communicationСhannel.paint(g, transact.getWorkTime());
                communicationСhannel.workTime_statistic_inc();
                if (transact.getWorkTime() != communicationСhannel.getWorkTime()) {
                    communicationСhannel.package_count_int();
                    return;
                } else {
                    communicationСhannel.setBusy(false);
                    transact.setWorkTime(0);
                    transact.setWorkPlace(null);
                }
            }
        }


        //Если мы достигли какой-то точки из пути, нам нужно ее удалить из оставшегося пути
        if (transact.getTrace().getPoints().get(0).equals(transact.getPosition())) {
            transact.deleteFirstTracePoint();
        }

        if (transact.getTrace().getPoints().size() == 0) {
            transact.setTraceToNull();
            return;
        }

        if (transact.getTrace().getPoints().get(0).getX() > transact.getPosition().getX()) {
            transact.updateX(Package.PLUS);
            return;
        }

        if (transact.getTrace().getPoints().get(0).getX() < transact.getPosition().getX()) {
            transact.updateX(Package.MINUS);
            return;
        }

        if (transact.getTrace().getPoints().get(0).getY() > transact.getPosition().getY()) {
            transact.updateY(Package.PLUS);
            return;
        }

        if (transact.getTrace().getPoints().get(0).getY() < transact.getPosition().getY()) {
            transact.updateY(Package.MINUS);
        }
    }

}
