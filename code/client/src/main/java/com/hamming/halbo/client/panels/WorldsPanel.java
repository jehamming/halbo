package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.HALBOClientController;
import com.hamming.halbo.model.dto.WorldDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class WorldsPanel extends JPanel {

    private JList<WorldDto> listOfWorlds;
    private DefaultListModel listModel;
    private HALBOClientController controller;

    public WorldsPanel(HALBOClientController controller) {
        this.controller = controller;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Worlds"));
        listModel = new DefaultListModel();
        listOfWorlds = new JList<WorldDto>(listModel);
        listOfWorlds.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    WorldDto world = listOfWorlds.getSelectedValue();
                    if (world != null) {
                        controller.worldSelected(world);
                    }
                }
            }
        });
        listOfWorlds.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfWorlds);
        add(scrollPane);
    }

    public void empty() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.removeAllElements();
            }
        });
    }

    public void addWorld(WorldDto world) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(world);
            }
        });
    }

    public void addWorlds(List<WorldDto> worlds) {
        for (WorldDto world: worlds) {
            addWorld(world);
        }
    }
}
