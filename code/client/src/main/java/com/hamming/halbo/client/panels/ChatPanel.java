package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ChatPanel extends JPanel {

    private BaseWindow client;
    private DefaultListModel listModel;


    public ChatPanel() {
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Chat"));
        //JScrollPane scrollPane = new JScrollPane(chatArea);
        //add(scrollPane);
    }

    public void empty() {
        //TODO Implement
    }


}
