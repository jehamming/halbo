package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.MoveController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementPanel extends JPanel{

    private MoveController moveController;

    public MovementPanel(MoveController moveController) {
        this.moveController = moveController;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Movement"));
        add(new JLabel("Use WSAD to Move)"));
    }


}
