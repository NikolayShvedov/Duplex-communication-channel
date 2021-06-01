package com.duplex_channel.GUI.Dialog;

import com.duplex_channel.GUI.Utils.Utils;
import com.duplex_channel.GUI.Frame.Frame;
import javax.swing.*;
import java.awt.*;

public class Dialog extends JDialog {

    private JPanel grid;

    public Dialog() {
        setTitle("Работа дуплексного канала связи");
        setSize(new Dimension(400, 250));
        setLocationRelativeTo(null);
        setResizable(false);

        //глобальные настройки
        grid = new JPanel();
        GridLayout layout = new GridLayout(5, 0, 5, 12);
        grid.setLayout(layout);

        //количество запусков
        JPanel runCountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel runCountLabel = new JLabel("Количество моделирований:");
        runCountPanel.add(runCountLabel);
        JTextField runCountText = new JTextField(17);
        runCountPanel.add(runCountText);
        grid.add(runCountPanel);

        //Задержка
        JPanel delayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel delayLabel = new JLabel("Время поступления пакетов(мс):");
        delayPanel.add(delayLabel);
        JTextField delayText = new JTextField(15);
        delayPanel.add(delayText);
        grid.add(delayPanel);

        //Разброс генерации
        JPanel runDeviationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel runDeviationLabel = new JLabel("Среднее отклонение времени(мс):");
        runDeviationPanel.add(runDeviationLabel);
        JTextField runDeviationText = new JTextField(14);
        runDeviationPanel.add(runDeviationText);
        grid.add(runDeviationPanel);

        //Запуск моделирований
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton button = new JButton("Запуск");
        buttonPanel.add(button);
        grid.add(buttonPanel);

        button.addActionListener(l -> {
            if (!delayText.getText().isEmpty()) {
                Utils.DELAY = Integer.parseInt(delayText.getText());
            }

            if (!runCountText.getText().isEmpty()) {
                Utils.RUN_COUNT = Integer.parseInt(runCountText.getText());
            }

            if (!runDeviationText.getText().isEmpty()) {
                Utils.DEVIATION_GENERATE_TIME = Integer.parseInt(runDeviationText.getText());
            }

            new Frame();
            setVisible(false);
        });

        getContentPane().add(grid);
        setVisible(true);
    }
}
