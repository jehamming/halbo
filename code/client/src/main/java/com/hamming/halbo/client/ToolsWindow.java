package com.hamming.halbo.client;


import com.hamming.halbo.client.panels.ChatPanel;
import com.hamming.halbo.client.panels.UsersPanel;

import javax.swing.*;
import java.awt.*;

public class ToolsWindow extends JFrame {

    private UsersPanel usersPanel;
    private ChatPanel chatPanel;
    private BaseWindow clientWindow;

    public ToolsWindow(BaseWindow clientWindow) {
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

        getContentPane().add(mainPainel);

        pack();
        setVisible(true);
    }


    public void emptyPanels() {
        usersPanel.empty();
        chatPanel.empty();
    }


}
