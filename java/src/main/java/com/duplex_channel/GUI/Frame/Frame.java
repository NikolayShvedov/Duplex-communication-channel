package com.duplex_channel.GUI.Frame;

import com.duplex_channel.GUI.Utils.Utils;
import com.duplex_channel.GUI.Q_Schema.PanelSchema;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        setTitle("Работа дуплексного канала связи");
        setSize(new Dimension(Utils.WIDTH, Utils.HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(new PanelSchema());
        setResizable(false);
        setVisible(true);
    }
}
