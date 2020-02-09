package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;


    public ChatPanel(HALBOClientWindow clientWindow) {
        this.client = clientWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Chat"));
        //JScrollPane scrollPane = new JScrollPane(chatArea);
        //add(scrollPane);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        //TODO IMplement
        System.out.println("Not implemented yet");
    }


    public void empty() {
        //TODO Implement
    }


}
