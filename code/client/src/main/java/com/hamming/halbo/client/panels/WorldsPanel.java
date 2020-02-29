package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.WorldController;
import com.hamming.halbo.client.interfaces.WorldListener;
import com.hamming.halbo.model.dto.WorldDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WorldsPanel extends JPanel implements WorldListener {

    private JList<WorldDto> listOfWorlds;
    private DefaultListModel listModel;
    private WorldController worldController;
    private BaseWindow baseWindow;


    public WorldsPanel(BaseWindow window, WorldController controller) {
        this.baseWindow = window;
        this.worldController = controller;
        worldController.addWorldListener(this);
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
                        worldController.worldSelected(world);
                        baseWindow.getContinentsPanel().empty();
                        baseWindow.getCitiesPanel().empty();
                    }
                }
            }
        });
        listOfWorlds.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfWorlds);
        add(scrollPane);
    }

    public void empty() {
        listModel.removeAllElements();
    }


    @Override
    public void worldAdded(WorldDto world) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(world);
            }
        });
    }

    @Override
    public void worldDeleted(WorldDto world) {

    }
}
