package com.hamming.halbo.client;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class WorldsPanel extends JPanel {

    private HALBOClientWindow client;
    private JList<String> listOfWorlds;
    private DefaultListModel listModel;

    public WorldsPanel(HALBOClientWindow client) {
        this.client = client;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Worlds"));

        listModel = new DefaultListModel();
        listOfWorlds = new JList<String>(listModel);
        listOfWorlds.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfWorlds);
        add(scrollPane);
    }

    public void receive( String s) {
        System.out.println("WORLDSPANEL-received:" + s);
        String[] arr = s.split(StringUtils.delimiter);
        if (arr.length >= 2) {
            String worldId = arr[0];
            String worldName = arr[1];
            listModel.addElement(worldName);
        }
    }




}
