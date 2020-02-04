package com.hamming.halbo.client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CitiesPanel extends JPanel {

    public CitiesPanel() {
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Cities"));

        JList<String> listOfCities = new JList<String>();
        listOfCities.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfCities);
        add(scrollPane);
    }
}
