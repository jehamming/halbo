package com.hamming.halbo.client;


import com.hamming.halbo.client.panels.UsersPanel;
import com.hamming.halbo.game.Protocol;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChatWindow extends JFrame {

    private UsersPanel usersPanel;
    private HALBOClientWindow clientWindow;

    public ChatWindow(HALBOClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        initWindow();
    }

    public void initWindow() {
        setTitle("Users & Chat");
        setPreferredSize(new Dimension(800,250));
        setLocation(400,670);

        JPanel mainPainel = new JPanel();
        mainPainel.setBorder(new TitledBorder("Login"));
        mainPainel.setLayout(new GridLayout(1,2,5,5));

        usersPanel = new UsersPanel(clientWindow);
        mainPainel.add(usersPanel);

        JPanel chatPanel = new JPanel();
        chatPanel.setPreferredSize(new Dimension(400,200));
        mainPainel.add(chatPanel);


        getContentPane().add(mainPainel);

        pack();
        setVisible(true);
    }


    public void registerReceivers() {
        clientWindow.getClient().registerReceiver(Protocol.Command.USERCONNECTED, usersPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.USERDISCONNECTED, usersPanel);
    }


}
