package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.DataController;
import com.hamming.halbo.client.interfaces.IWorldWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WorldsPanel extends JPanel implements IWorldWindow {

    private JList<WorldDto> listOfWorlds;
    private DefaultListModel listModel;
    private DataController dataController;


    public WorldsPanel(DataController controller) {
        this.dataController = controller;
        dataController.setWorldWindow(this);
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
                        dataController.worldSelected(world);
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


    public void addWorld(WorldDto dto) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(dto);
            }
        });
    }

    public WorldDto getSelectedWorld() {
        return listOfWorlds.getSelectedValue();
    }
}
