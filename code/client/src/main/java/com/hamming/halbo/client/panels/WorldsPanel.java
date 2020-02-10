package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WorldsPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private JList<WorldDto> listOfWorlds;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;

    public WorldsPanel(HALBOClientWindow client) {
        this.client = client;
        protocolHandler = new ProtocolHandler();
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
                        worldSelected(world);
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

    public void worldSelected(WorldDto world) {
        client.getContinentsPanel().empty();
        client.getCitiesPanel().empty();
        client.getBaseplatesPanel().empty();
        String s = protocolHandler.getGetContinentsCommand(world.getId());
        client.send(s);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.GETWORLDS)) {
            WorldDto world = new WorldDto();
            world.setValues(data);
            listModel.addElement(world);
        }
    }

    public WorldDto getSelectedWorld() {
        return listOfWorlds.getSelectedValue();
    }
}
