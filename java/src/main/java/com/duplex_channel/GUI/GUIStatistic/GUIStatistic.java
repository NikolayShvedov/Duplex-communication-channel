package com.duplex_channel.GUI.GUIStatistic;

import com.duplex_channel.GUI.Utils.Components.CommunicationСhannel;
import com.duplex_channel.GUI.Utils.Utils;
import com.duplex_channel.Statistic.Statistic;

import javax.swing.*;

public class GUIStatistic {

    private StatisticToFile statisticToFile;

    public GUIStatistic(int simulationNumber, Statistic instance) {

        statisticToFile = new StatisticToFile();

        JDialog dialog = new JDialog();
        dialog.setTitle("Статистика");
        dialog.setSize(400, 450);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.append("Номер моделирования: " + simulationNumber + "\n");
        textArea.append("Время моделирования: " + Utils.TIME + "\n");
        textArea.append("Количество сгенерированных пакетов в пункте А = " + instance.getPackageCountA() + "\n");
        textArea.append("Количество сгенерированных пакетов в пункте B = " + instance.getPackageCountB() + "\n");
        textArea.append("Количество отклоненных пакетов = " + instance.getDeclineCount() + "\n");
        textArea.append("Количество обработанных пакетов = " + instance.getDonePackages() + "\n");
        textArea.append("\n");

        String statisticModeling = "Номер моделирования: " + simulationNumber + "\n" +
                "Время моделирования: " + Utils.TIME + "\n" +
                "Количество сгенерированных пакетов в пункте А = " + instance.getPackageCountA() + "\n" +
                "Количество сгенерированных пакетов в пункте B = " + instance.getPackageCountB() + "\n" +
                "Количество отклоненных пакетов = " + instance.getDeclineCount() + "\n" +
                "Количество обработанных пакетов = " + instance.getDonePackages() + "\n" + "\n";

        for (CommunicationСhannel сommunicationСhannel : instance.getCommunicationСhannels()) {
            textArea.append("\t" + сommunicationСhannel.getTitle() + "\n");
            textArea.append("Время работы  :  " + сommunicationСhannel.getStatistic_workTime() / Utils.RUN_COUNT + "\n");
            double i = (сommunicationСhannel.getStatistic_workTime()/((double)Utils.RUN_COUNT * Utils.TIME)) * 100;
            if (i > 100)
                i = 100;
            textArea.append("Загруженность (%) :  " + String.format("%.2f%n", i));
            textArea.append("Количество пакетов  :  " + сommunicationСhannel.getPackage_count() + "\n");
            textArea.append("****\n");

            statisticModeling += "\t" + сommunicationСhannel.getTitle() + "\n" +
                    "Время работы  :  " + сommunicationСhannel.getStatistic_workTime() / Utils.RUN_COUNT + "\n" +
                    "Загруженность (%) :  " + String.format("%.2f%n", i) +
                    "Количество пакетов  :  " + сommunicationСhannel.getPackage_count() + "\n";
        }
        statisticToFile.modelingFileСreation();
        statisticToFile.modelingFileFilling(statisticModeling);
        dialog.add(textArea);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}