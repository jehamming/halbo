package com.hamming.halbo.client;


import com.hamming.halbo.client.panels.ChatPanel;
import com.hamming.halbo.client.panels.MovementPanel;
import com.hamming.halbo.client.panels.UsersPanel;
import com.hamming.halbo.game.Protocol;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ToolsWindow extends JFrame {

    private UsersPanel usersPanel;
    private ChatPanel chatPanel;
    private MovementPanel movementPanel;
    private HALBOClientWindow clientWindow;

    public ToolsWindow(HALBOClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        initWindow();
    }

    public void initWindow() {
        setTitle("Tools");
        setPreferredSize(new Dimension(800,170));
        setLocation(400,670);

        JPanel mainPainel = new JPanel();
        mainPainel.setLayout(new GridLayout(1,3,5,5));

        usersPanel = new UsersPanel(clientWindow);
        mainPainel.add(usersPanel);

        chatPanel = new ChatPanel(clientWindow);
        mainPainel.add(chatPanel);

        movementPanel = new MovementPanel(clientWindow, this);
        mainPainel.add(movementPanel);

        getContentPane().add(mainPainel);

        pack();
        setVisible(true);
    }


    public void registerReceivers() {
        clientWindow.getClient().registerReceiver(Protocol.Command.USERCONNECTED, usersPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.USERDISCONNECTED, usersPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.MOVE, movementPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.LOCATION, movementPanel);
    }


    public void emptyPanels() {
        usersPanel.empty();
        chatPanel.empty();
        movementPanel.empty();;
    }


}
