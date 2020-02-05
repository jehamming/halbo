package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WorldsPanel extends JPanel {

    private HALBOClientWindow client;
    private JList<WorldDto> listOfWorlds;
    private DefaultListModel listModel;

    public WorldsPanel(HALBOClientWindow client) {
        this.client = client;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Worlds"));
        listModel = new DefaultListModel();
        listOfWorlds = new JList<WorldDto>(listModel);
        listOfWorlds.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                WorldDto world = listOfWorlds.getSelectedValue();
                if (world != null ) {
                    client.worldSelected(world);
                }
            }
        });
        listOfWorlds.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfWorlds);
        add(scrollPane);
    }

    public void receive( String s) {
        String[] arr = s.split(StringUtils.delimiter);
        WorldDto world = new WorldDto();
        world.setValues(arr);
        listModel.addElement(world);
    }



    public void empty() {
        listModel.removeAllElements();
    }
}
