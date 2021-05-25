package GUI.GUIStatistic;

import GUI.Utils.Components.CommunicationСhannel;
import GUI.Utils.Utils;
import Statistic.Statistic;

import javax.swing.*;

public class GUIStatistic {

    public GUIStatistic() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Статистика");
        dialog.setSize(400, 450);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.append("Количество моделирований: " + Utils.RUN_COUNT + "\n");
        textArea.append("Общее время моделирования: " + Utils.TIME * Utils.RUN_COUNT+"\n");
        textArea.append("Среднее количество сгенерированных пакетов в пункте А = " + Statistic.instance.getPackageCountA() + "\n");
        textArea.append("Среднее количество сгенерированных пакетов в пункте B = " + Statistic.instance.getPackageCountB() + "\n");
        textArea.append("Среднее количество отклоненных пакетов = " + Statistic.instance.getDeclineCount() + "\n");
        textArea.append("Среднее количество обработанных пакетов = " + Statistic.instance.getDonePackages() + "\n");
        textArea.append("\n");
        for (CommunicationСhannel сommunicationСhannel : Statistic.instance.getCommunicationСhannels()) {
            textArea.append("\t" + сommunicationСhannel.getTitle() + "\n");
            textArea.append("Среднее время работы  :  " + сommunicationСhannel.getStatistic_workTime() / Utils.RUN_COUNT + "\n");
            double i = (сommunicationСhannel.getStatistic_workTime()/((double)Utils.RUN_COUNT * Utils.TIME)) * 100;
            if (i > 100)
                i = 100;
            textArea.append("Среднее время работы (%) :  " + String.format("%.2f%n", i));
            textArea.append("Среднее количество пакетов  :  " + сommunicationСhannel.getPackage_count() / Utils.RUN_COUNT + "\n");
            textArea.append("****\n");
        }
        dialog.add(textArea);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
