package com.duplex_channel.Statistic;

import com.duplex_channel.GUI.Utils.Components.CommunicationСhannel;
import com.duplex_channel.GUI.Utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Statistic {

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

    public void addDeclinePackageCount(int declineCount){
        this.declineCount += declineCount;
    }

    public void addPackageCountA(int transactCount) {
        this.packageCountA += transactCount;
    }

    public void addPackageCountB(int transactCount) {
        this.packageCountB += transactCount;
    }

    public void addDonePackages(int donePackages) {
        this.donePackages += donePackages;
    }

}

