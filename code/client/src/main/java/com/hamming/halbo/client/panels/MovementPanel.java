package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class MovementPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;


    private JLabel lblCurrentLocation;
    private JLabel lblRequest;


    public MovementPanel(HALBOClientWindow clientWindow) {
        this.client = clientWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Position"));
        add( new JLabel("User WSAD to Move"));
        //JScrollPane scrollPane = new JScrollPane(chatArea);
        //add(scrollPane);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
            if (cmd.equals(Protocol.Command.GETCONTINENTS)) {
                ContinentDto c = new ContinentDto();
                c.setValues(data);
                listModel.addElement(c);
            }
    }


    public void empty() {
        //TODO Implement
    }


}
