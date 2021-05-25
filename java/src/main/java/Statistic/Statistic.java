package Statistic;

import GUI.Utils.Components.CommunicationСhannel;
import GUI.Utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Statistic {

    public static final Statistic instance = new Statistic();

    private double packageCountA = 0;
    private double packageCountB = 0;
    private double donePackages = 0;
    private ArrayList<CommunicationСhannel> communicationСhannels;
    private int declineCount = 0;

    public void addCommunicationСhannel(CommunicationСhannel communicationСhannel) {
        if (communicationСhannels == null)
            communicationСhannels = new ArrayList<>();
        communicationСhannels.add(communicationСhannel);
    }

    public void addDeclineTransactCount(int declineCount){
        this.declineCount += declineCount / Utils.RUN_COUNT;
    }

    public void addPackageCountA(int transactCount) {
        this.packageCountA += transactCount / Utils.RUN_COUNT;
    }

    public void addPackageCountB(int transactCount) {
        this.packageCountB += transactCount / Utils.RUN_COUNT;
    }

    public void addDonePackages(int donePackages) {
        this.donePackages += donePackages / Utils.RUN_COUNT;
    }
}
