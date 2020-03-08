package com.hamming.halbo.client;


import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.controllers.UserController;
import com.hamming.halbo.client.panels.ChatPanel;
import com.hamming.halbo.client.panels.MovementPanel;
import com.hamming.halbo.client.panels.UsersPanel;

import javax.swing.*;
import java.awt.*;

public class ToolsWindow extends JFrame {

    private UsersPanel usersPanel;
    private ChatPanel chatPanel;
    private MovementPanel movementPanel;
    private UserController userController;
    private MoveController moveController;

    public ToolsWindow(Controllers controllers) {
        this.userController = controllers.getUserController();
        this.moveController = controllers.getMoveController();
        initWindow();
    }

    public void initWindow() {
        setTitle("Tools");
        setPreferredSize(new Dimension(900,170));
        setLocation(360,600);

        JPanel mainPainel = new JPanel();
        mainPainel.setLayout(new GridLayout(1,3,5,5));

        usersPanel = new UsersPanel(userController);
        mainPainel.add(usersPanel);

        chatPanel = new ChatPanel();
        mainPainel.add(chatPanel);

        movementPanel = new MovementPanel(moveController);
        mainPainel.add(movementPanel);

        getContentPane().add(mainPainel);

        pack();
        setVisible(true);
    }


    public void emptyPanels() {
        usersPanel.empty();
        chatPanel.empty();
    }


}
